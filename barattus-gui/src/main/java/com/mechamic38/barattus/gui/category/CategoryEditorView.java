package com.mechamic38.barattus.gui.category;

import com.mechamic38.barattus.core.category.Category;
import com.mechamic38.barattus.core.category.CategoryField;
import com.mechamic38.barattus.gui.common.BaseView;
import com.mechamic38.barattus.gui.common.Views;
import com.mechamic38.barattus.gui.util.CellFactoryProvider;
import com.mechamic38.barattus.gui.util.I18NButtonTypes;
import com.mechamic38.barattus.i18n.api.I18N;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class CategoryEditorView extends BaseView implements Initializable {

    private final ICategoryEditorViewModel viewModel;
    @FXML
    protected ScrollPane graphic;
    @FXML
    private TextField hierarchyNameField;
    @FXML
    private TextField parentNameField;
    @FXML
    private TextField categoryNameField;
    @FXML
    private TextField categoryDescriptionField;
    @FXML
    private TextField fieldNameField;
    @FXML
    private TextField subcatNameField;
    @FXML
    private TextField subcatDescriptionField;
    @FXML
    private CheckBox fieldMandatoryBox;
    @FXML
    private Button goToHierarchyButton;
    @FXML
    private Button goToParentButton;
    @FXML
    private Button confirmChangesButton;
    @FXML
    private Button addFieldButton;
    @FXML
    private Button deleteFieldButton;
    @FXML
    private Button editFieldButton;
    @FXML
    private Button addSubcatButton;
    @FXML
    private Button backButton;
    @FXML
    private Button editSubcatButton;
    @FXML
    private ComboBox<CategoryField.Type> fieldTypeBox;

    @FXML
    private TableView<CategoryField> fieldTable;
    @FXML
    private TableColumn<CategoryField, String> fieldNameCol;
    @FXML
    private TableColumn<CategoryField, String> fieldTypeCol;
    @FXML
    private TableColumn<CategoryField, Boolean> fieldMandatoryCol;

    @FXML
    private TableView<Category> subcatTable;
    @FXML
    private TableColumn<Category, String> catNameCol;
    @FXML
    private TableColumn<Category, String> catDescriptionCol;
    @FXML
    private TableColumn<Category, Boolean> catParentCol;

    public CategoryEditorView(ICategoryEditorViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private void onGotoHierarchy() {
        viewModel.gotoHierarchy();
    }

    @FXML
    private void onGotoParent() {
        viewModel.gotoParent();
    }

    @FXML
    private void onApply() {
        String description = categoryDescriptionField.getText();
        viewModel.updateCategory(description);
    }

    @FXML
    private void onAddField() {
        String fieldName = fieldNameField.getText();
        CategoryField.Type fieldType = fieldTypeBox.getSelectionModel().getSelectedItem();
        boolean isMandatory = fieldMandatoryBox.isSelected();

        viewModel.addField(fieldName, fieldType, isMandatory);
    }

    @FXML
    private void onDeleteField() {
        CategoryField field = fieldTable.getSelectionModel().getSelectedItem();

        getActivity().showConfirmationDialog(
                I18N.getValue("category.editor.title"),
                I18N.getValue("category.warning.field.delete"),
                buttonType -> {
                    if (buttonType.equals(I18NButtonTypes.YES)) {
                        viewModel.deleteField(field);
                    }
                }
        );
    }

    @FXML
    private void onEditField() {
        viewModel.setFieldToEdit(
                fieldTable.getSelectionModel().getSelectedItem()
        );
        this.changeContent(Views.FIELD_EDITOR);
    }

    @FXML
    private void onAddCategory() {
        String subcatName = subcatNameField.getText();
        String subcatDescription = subcatDescriptionField.getText();

        viewModel.addCategory(subcatName, subcatDescription);
    }

    @FXML
    private void onBack() {
        this.changeContent(Views.CATEGORY_LIST);
    }

    @FXML
    private void onEditCategory() {
        viewModel.setCategoryToEdit(
                subcatTable.getSelectionModel().getSelectedItem()
        );
        this.changeContent(Views.CATEGORY_EDITOR);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setViewProperties();
        setCustomFactories();

        viewModel.errorProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isBlank()) return;
            getActivity().showErrorDialog(
                    I18N.getValue("category.editor.title"),
                    I18N.getValue(newValue),
                    buttonType -> {
                        viewModel.errorProperty().set("");
                    }
            );
        });

        viewModel.categoryProperty().addListener((observable, oldValue, newValue) -> {
            hierarchyNameField.setText(newValue.getHierarchyName());
            parentNameField.setText(newValue.getParentName());
            categoryNameField.setText(newValue.getName());
            categoryDescriptionField.setText(newValue.getDescription());
        });

        subcatTable.itemsProperty().bind(viewModel.subcategoriesProperty());
        fieldTable.itemsProperty().bind(viewModel.fieldsProperty());
        fieldTypeBox.itemsProperty().set(FXCollections.observableList(
                Arrays.stream(CategoryField.Type.values()).toList()
        ));
    }

    private void setViewProperties() {
        addFieldButton.disableProperty().bind(Bindings.or(
                fieldNameField.textProperty().isEmpty(),
                fieldTypeBox.getSelectionModel().selectedItemProperty().isNull()
        ));
        deleteFieldButton.disableProperty().bind(
                fieldTable.getSelectionModel().selectedItemProperty().isNull()
        );
        editFieldButton.disableProperty().bind(
                fieldTable.getSelectionModel().selectedItemProperty().isNull()
        );
        addSubcatButton.disableProperty().bind(Bindings.or(
                subcatNameField.textProperty().isEmpty(),
                subcatDescriptionField.textProperty().isEmpty()
        ));
        editSubcatButton.disableProperty().bind(
                subcatTable.getSelectionModel().selectedItemProperty().isNull()
        );
        goToParentButton.disableProperty().bind(viewModel.canGotoParentProperty().not());
    }

    private void setCustomFactories() {
        catNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        catDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        catParentCol.setCellValueFactory(new PropertyValueFactory<>("parentName"));

        fieldNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        fieldTypeCol.setCellValueFactory(cell -> new SimpleStringProperty(
                I18N.getValue(cell.getValue().getFieldType().i18n)
        ));
        fieldMandatoryCol.setCellValueFactory(new PropertyValueFactory<>("mandatory"));

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
