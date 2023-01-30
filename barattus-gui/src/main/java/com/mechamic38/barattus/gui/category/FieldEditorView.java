package com.mechamic38.barattus.gui.category;

import com.mechamic38.barattus.core.category.CategoryField;
import com.mechamic38.barattus.gui.common.BaseView;
import com.mechamic38.barattus.gui.common.Views;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class FieldEditorView extends BaseView implements Initializable {

    @FXML
    protected GridPane graphic;
    private IFieldEditorViewModel viewModel;
    private Consumer<Views> viewChangeAction;
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

        viewModel.updateField(fieldName, fieldType, mandatory);
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
        if (viewChangeAction != null) viewChangeAction.accept(view);
    }

    @Override
    public void setViewChangeAction(Consumer<Views> viewChangeAction) {
        this.viewChangeAction = viewChangeAction;
    }
}
