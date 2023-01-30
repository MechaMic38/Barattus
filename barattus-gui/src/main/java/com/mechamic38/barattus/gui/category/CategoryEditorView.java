package com.mechamic38.barattus.gui.category;

import com.mechamic38.barattus.core.category.Category;
import com.mechamic38.barattus.core.category.CategoryField;
import com.mechamic38.barattus.gui.common.BaseView;
import com.mechamic38.barattus.gui.common.Views;
import com.mechamic38.barattus.i18n.api.I18N;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class CategoryEditorView extends BaseView implements Initializable {

    private final ICategoryEditorVIewModel viewModel;
    @FXML
    protected ScrollPane graphic;
    private Consumer<Views> viewChangeAction;
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

    public CategoryEditorView(ICategoryEditorVIewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private void onGotoHierarchy() {
        viewModel.setCategoryToEdit(
                hierarchyNameField.getText()
        );
    }

    @FXML
    private void onGotoParent() {
        viewModel.setCategoryToEdit(
                parentNameField.getText()
        );
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
                "Delete Field",
                I18N.getValue("category.warning.field.delete"),
                buttonType -> {
                    if (buttonType.equals(ButtonType.YES)) {
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
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
    }

    @Override
    public Parent getGraphic() {
        return graphic;
    }

    @Override
    public void changeContent(Views view) {

    }

    @Override
    public void setViewChangeAction(Consumer<Views> viewChangeAction) {
        this.viewChangeAction = viewChangeAction;
    }
}
