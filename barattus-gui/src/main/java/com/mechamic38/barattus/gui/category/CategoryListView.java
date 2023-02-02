package com.mechamic38.barattus.gui.category;

import com.mechamic38.barattus.core.category.Category;
import com.mechamic38.barattus.gui.common.BaseView;
import com.mechamic38.barattus.gui.common.Views;
import com.mechamic38.barattus.gui.util.CellFactoryProvider;
import com.mechamic38.barattus.gui.util.I18NButtonTypes;
import com.mechamic38.barattus.i18n.api.I18N;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class CategoryListView extends BaseView implements Initializable {

    private final ICategoryListViewModel viewModel;
    @FXML
    protected GridPane graphic;
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
        Category hierarchy = hierarchyBox.getSelectionModel().getSelectedItem();
        if (hierarchy != null) {
            viewModel.updateCategoryList(
                    hierarchyBox.getSelectionModel().getSelectedItem()
            );
        }
    }

    @FXML
    public void onModifyCategory() {
        viewModel.setCategoryToEdit(
                categoryTable.getSelectionModel().getSelectedItem()
        );
        this.changeContent(Views.CATEGORY_EDITOR);
    }

    @FXML
    public void onModifyHierarchy() {
        viewModel.setCategoryToEdit(
                hierarchyBox.getSelectionModel().getSelectedItem()
        );
        this.changeContent(Views.CATEGORY_EDITOR);
    }

    @FXML
    public void onLoad() {
        getActivity().showWarningDialog(
                I18N.getValue("category.manager.title"),
                I18N.getValue("category.load.warning"),
                buttonType -> {
                    if (buttonType.equals(I18NButtonTypes.YES)) {
                        FileChooser fc = new FileChooser();
                        fc.getExtensionFilters().add(
                                new FileChooser.ExtensionFilter("Json Files", "*.json")
                        );

                        File selectedFile = fc.showOpenDialog(getActivity().getContextWindow());
                        if (selectedFile == null) return;

                        if (viewModel.loadFromFile(selectedFile.getPath())) {
                            getActivity().showInformationDialog(
                                    I18N.getValue("category.manager.title"),
                                    I18N.getValue("category.load.success"),
                                    ignored -> {
                                    }
                            );
                        }
                    }
                }
        );
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAdminProperties();
        setViewProperties();
        setCustomFactories();

        viewModel.errorProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isBlank()) return;
            getActivity().showErrorDialog(
                    I18N.getValue("category.manager.title"),
                    I18N.getValue(newValue),
                    buttonType -> {
                        viewModel.errorProperty().set("");
                    }
            );
        });

        hierarchyBox.itemsProperty().bind(viewModel.rootCategoriesProperty());
        categoryTable.itemsProperty().bind(viewModel.leafCategoriesProperty());
    }

    private void setAdminProperties() {
        BooleanProperty admin = viewModel.adminProperty();

        setNodeVisibility(hierarchyNameField, admin);
        setNodeVisibility(hierarchyDescriptionField, admin);
        setNodeVisibility(loadFromFileButton, admin);
        setNodeVisibility(selectCategoryButton, admin);
        setNodeVisibility(selectHierarchyButton, admin);
        setNodeVisibility(newHierarchyButton, admin);
    }

    private void setNodeVisibility(Node node, ReadOnlyBooleanProperty visible) {
        node.visibleProperty().bind(visible);
        node.managedProperty().bind(visible);
    }

    private void setViewProperties() {
        newHierarchyButton.disableProperty().bind(Bindings.or(
                hierarchyNameField.textProperty().isEmpty(),
                hierarchyDescriptionField.textProperty().isEmpty()
        ));
        selectCategoryButton.disableProperty().bind(categoryTable.getSelectionModel().selectedItemProperty().isNull());
        selectHierarchyButton.disableProperty().bind(hierarchyBox.getSelectionModel().selectedItemProperty().isNull());
    }

    private void setCustomFactories() {

        catNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        catDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        catParentCol.setCellValueFactory(new PropertyValueFactory<>("parentName"));

        hierarchyBox.setCellFactory(listView -> CellFactoryProvider.getCategoryBoxCell());
        hierarchyBox.setButtonCell(CellFactoryProvider.getCategoryBoxCell());
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
