package com.mechamic38.barattus.gui.category;

import com.mechamic38.barattus.core.category.Category;
import com.mechamic38.barattus.core.category.ICategoryRepository;
import com.mechamic38.barattus.core.category.ICategoryService;
import com.mechamic38.barattus.core.user.UserRole;
import com.mechamic38.barattus.gui.common.SessionState;
import com.mechamic38.barattus.util.Result;
import javafx.beans.property.*;
import javafx.collections.FXCollections;

import java.util.List;

public class CategoryListViewModel implements ICategoryListViewModel {

    private final ICategoryService categoryService;
    private final ICategoryRepository categoryRepository;

    private final BooleanProperty admin = new SimpleBooleanProperty(false);
    private final StringProperty error = new SimpleStringProperty("");
    private final ListProperty<Category> rootCategories = new SimpleListProperty<>();
    private final ListProperty<Category> leafCategories = new SimpleListProperty<>();

    public CategoryListViewModel(ICategoryService categoryService, ICategoryRepository categoryRepository) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void addHierarchy(String name, String description) {
        Result<Category> result = categoryService.addHierarchy(name, description);

        if (result.isError()) {
            error.set(result.getError());
        } else {
            rootCategories.add(result.getResult());
        }
    }

    @Override
    public void updateCategoryList(Category hierarchy) {
        leafCategories.set(FXCollections.observableList(
                categoryService.getSubcategories(hierarchy)
        ));
    }

    @Override
    public void setCategoryToEdit(Category category) {
        SessionState.getInstance().setCategory(category);
    }

    @Override
    public boolean loadFromFile(String path) {
        Result<List<Category>> result = categoryService.loadParamsFromFile(path);

        if (result.isError()) {
            errorProperty().set(result.getError());
            return false;
        } else {
            setCategoryProperties(categoryService.getHierarchies());
            return true;
        }
    }

    @Override
    public void initialize() {
        setProperties();
        setCategoryProperties(categoryService.getHierarchies());
    }

    private void setProperties() {
        admin.set(
                SessionState.getInstance().getUser().getRole().equals(UserRole.CONFIGURATOR)
        );
    }

    private void setCategoryProperties(List<Category> hierarchies) {
        rootCategories.set(FXCollections.observableList(hierarchies));
        leafCategories.set(FXCollections.emptyObservableList());
    }

    @Override
    public BooleanProperty adminProperty() {
        return admin;
    }

    @Override
    public StringProperty errorProperty() {
        return error;
    }

    @Override
    public ListProperty<Category> rootCategoriesProperty() {
        return rootCategories;
    }

    @Override
    public ListProperty<Category> leafCategoriesProperty() {
        return leafCategories;
    }
}
