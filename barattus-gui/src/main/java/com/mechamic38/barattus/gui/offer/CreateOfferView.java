package com.mechamic38.barattus.gui.offer;

import com.mechamic38.barattus.core.category.Category;
import com.mechamic38.barattus.core.category.CategoryField;
import com.mechamic38.barattus.gui.common.BaseView;
import com.mechamic38.barattus.gui.common.CellFactoryProvider;
import com.mechamic38.barattus.gui.common.Views;
import com.mechamic38.barattus.i18n.api.I18N;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class CreateOfferView extends BaseView implements Initializable {

    private final ICreateOfferViewModel viewModel;
    private final HashMap<CategoryField, TextField> categoryFields;
    private Consumer<Views> viewChangeAction;
    @FXML
    private ScrollPane graphic;
    @FXML
    private GridPane fieldContainer;

    @FXML
    private TextField titleField;

    @FXML
    private ComboBox<Category> rootCategoryBox;
    @FXML
    private ComboBox<Category> leafCategoryBox;

    @FXML
    private Button publishButton;

    public CreateOfferView(ICreateOfferViewModel viewModel) {
        this.viewModel = viewModel;
        this.categoryFields = new LinkedHashMap<>();
    }

    @FXML
    private void onRootChange() {
        viewModel.loadSubcategories(
                rootCategoryBox.getSelectionModel().getSelectedItem()
        );
    }

    @FXML
    private void onLeafChange() {
        viewModel.loadCategoryFields(
                rootCategoryBox.getSelectionModel().getSelectedItem()
        );
    }

    @FXML
    private void onPublish() {
        boolean success = viewModel.publishOffer(
                leafCategoryBox.getSelectionModel().getSelectedItem(),
                titleField.getText(),
                extractFields()
        );

        if (success) {
            getActivity().showInformationDialog(
                    I18N.getValue("offer.create.title"),
                    I18N.getValue("offer.create.success"),
                    buttonType -> changeContent(Views.OFFER_DETAILS)
            );
        }
    }

    @Override
    public Parent getGraphic() {
        return graphic;
    }

    @Override
    public void changeContent(Views view) {
        if (viewChangeAction != null) viewChangeAction.accept(view);
    }

    @Override
    public void setViewChangeAction(Consumer<Views> viewChangeAction) {
        this.viewChangeAction = viewChangeAction;
    }

    @Override
    public void onViewCreated() {
        viewModel.initialize();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setViewProperties();
        setCustomFactories();

        viewModel.errorProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isBlank()) return;
            getActivity().showErrorDialog(
                    I18N.getValue("offer.create.title"),
                    I18N.getValue(newValue),
                    buttonType -> {
                        viewModel.errorProperty().set("");
                    }
            );
        });

        viewModel.fieldsProperty().addListener((observable, oldValue, fields) -> {
            removeFields();
            createFields(fields);
        });

        rootCategoryBox.itemsProperty().bind(viewModel.rootCategoriesProperty());
        leafCategoryBox.itemsProperty().bind(viewModel.leafCategoriesProperty());
    }

    private void setViewProperties() {
        leafCategoryBox.disableProperty().bind(
                rootCategoryBox.getSelectionModel().selectedItemProperty().isNull()
        );

        publishButton.disableProperty().bind(Bindings.or(
                Bindings.or(
                        rootCategoryBox.getSelectionModel().selectedItemProperty().isNull(),
                        leafCategoryBox.getSelectionModel().selectedItemProperty().isNull()
                ),
                titleField.textProperty().isEmpty()
        ));
    }

    private void setCustomFactories() {
        rootCategoryBox.setCellFactory(listView -> CellFactoryProvider.getCategoryBoxCell());
        rootCategoryBox.setButtonCell(CellFactoryProvider.getCategoryBoxCell());
        leafCategoryBox.setCellFactory(listView -> CellFactoryProvider.getCategoryBoxCell());
        leafCategoryBox.setButtonCell(CellFactoryProvider.getCategoryBoxCell());
    }

    private HashMap<CategoryField, String> extractFields() {
        HashMap<CategoryField, String> fields = new LinkedHashMap<>();

        categoryFields.forEach((field, textField) -> {
            fields.put(
                    field,
                    textField.getText().trim()
            );
        });

        return fields;
    }

    private void createFields(ObservableList<CategoryField> fields) {
        int row = 0;
        for (CategoryField field : fields) {
            TextField txtField = createField(field, row++);
            categoryFields.put(field, txtField);
        }
    }

    private TextField createField(CategoryField field, int row) {
        String labelText = (field.getMandatory()) ? field.getName() + "*" : field.getName();
        Label label = new Label(labelText);

        TextField txtField = new TextField();

        fieldContainer.add(label, 0, row, 2, 1);
        fieldContainer.add(txtField, 2, row, GridPane.REMAINING, 1);

        return txtField;
    }

    private void removeFields() {
        categoryFields.clear();
        fieldContainer.getChildren().clear();
    }
}
