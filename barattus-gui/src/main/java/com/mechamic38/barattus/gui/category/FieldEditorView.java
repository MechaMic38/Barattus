package com.mechamic38.barattus.gui.category;

import com.mechamic38.barattus.core.category.CategoryField;
import com.mechamic38.barattus.gui.common.BaseView;
import com.mechamic38.barattus.gui.common.Views;
import com.mechamic38.barattus.gui.util.CellFactoryProvider;
import com.mechamic38.barattus.i18n.api.I18N;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class FieldEditorView extends BaseView implements Initializable {

    @FXML
    protected GridPane graphic;
    private IFieldEditorViewModel viewModel;
    @FXML
    private TextField fieldNameField;
    @FXML
    private ComboBox<CategoryField.Type> fieldTypeBox;
    @FXML
    private CheckBox fieldMandatoryBox;
    @FXML
    private Button backButton;
    @FXML
    private Button applyButton;

    public FieldEditorView(IFieldEditorViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private void onBack() {
        this.changeContent(Views.CATEGORY_EDITOR);
    }

    @FXML
    private void onApply() {
        String fieldName = fieldNameField.getText();
        CategoryField.Type fieldType = fieldTypeBox.getSelectionModel().getSelectedItem();
        boolean mandatory = fieldMandatoryBox.isSelected();

        if (viewModel.updateField(fieldName, fieldType, mandatory)) {
            getActivity().showInformationDialog(
                    I18N.getValue("category.field.editor.title"),
                    I18N.getValue("category.field.update.success"),
                    buttonType -> {
                    }
            );
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setViewProperties();
        setCustomFactories();

        viewModel.errorProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isBlank()) return;
            getActivity().showErrorDialog(
                    I18N.getValue("category.field.editor.title"),
                    I18N.getValue(newValue),
                    buttonType -> {
                        viewModel.errorProperty().set("");
                    }
            );
        });

        viewModel.fieldProperty().addListener((observable, oldValue, newValue) -> {
            fieldNameField.textProperty().set(newValue.getName());
            fieldTypeBox.setValue(newValue.getFieldType());
            fieldMandatoryBox.selectedProperty().set(newValue.getMandatory());
        });

        fieldTypeBox.itemsProperty().set(FXCollections.observableList(
                Arrays.stream(CategoryField.Type.values()).toList()
        ));
    }

    private void setViewProperties() {
        applyButton.disableProperty().bind(Bindings.or(
                fieldNameField.textProperty().isEmpty(),
                fieldTypeBox.getSelectionModel().selectedItemProperty().isNull()
        ));
    }

    private void setCustomFactories() {
        fieldTypeBox.setCellFactory(listView -> CellFactoryProvider.getCategoryFieldTypeBoxCell());
        fieldTypeBox.setButtonCell(CellFactoryProvider.getCategoryFieldTypeBoxCell());
    }

    @Override
    public Parent getGraphic() {
        return graphic;
    }

    @Override
    public void onViewCreated() {
        viewModel.initialize();
    }
}
