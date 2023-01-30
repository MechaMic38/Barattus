package com.mechamic38.barattus.gui.category;

import com.mechamic38.barattus.core.category.Category;
import com.mechamic38.barattus.gui.common.BaseView;
import com.mechamic38.barattus.gui.common.Views;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class CategoryListView extends BaseView implements Initializable {

    private final ICategoryListViewModel viewModel;
    @FXML
    protected GridPane graphic;
    private Consumer<Views> viewChangeAction;
    @FXML
    private TextField hierarchyNameField;
    @FXML
    private TextField hierarchyDescriptionField;

    @FXML
    private ComboBox<Category> hierarchyBox;

    @FXML
    private Button newHierarchyButton;
    @FXML
    private Button loadFromFileButton;
    @FXML
    private Button selectCategoryButton;
    @FXML
    private Button selectHierarchyButton;

    @FXML
    private TableView<Category> categoryTable;
    @FXML
    private TableColumn<Category, String> catNameCol;
    @FXML
    private TableColumn<Category, String> catDescriptionCol;
    @FXML
    private TableColumn<Category, Boolean> catParentCol;

    public CategoryListView(ICategoryListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    public void onAddHierarchy() {
        String name = hierarchyNameField.getText();
        String description = hierarchyDescriptionField.getText();

        viewModel.addHierarchy(name, description);
    }

    @FXML
    public void onHierarchyChange() {
        viewModel.updateCategoryList(
                hierarchyBox.getSelectionModel().getSelectedItem()
        );
    }

    @FXML
    public void onModifyCategory() {
        viewModel.setCategoryToEdit(
                categoryTable.getSelectionModel().getSelectedItem()
        );
    }

    @FXML
    public void onModifyHierarchy() {
        viewModel.setCategoryToEdit(
                hierarchyBox.getSelectionModel().getSelectedItem()
        );
    }

    @FXML
    public void onLoad() {
        //TODO
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
