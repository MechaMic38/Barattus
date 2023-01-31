package com.mechamic38.barattus.gui.category;

import com.mechamic38.barattus.core.category.Category;
import com.mechamic38.barattus.core.category.CategoryField;
import com.mechamic38.barattus.core.category.ICategoryRepository;
import com.mechamic38.barattus.core.category.ICategoryService;
import com.mechamic38.barattus.gui.common.SessionState;
import com.mechamic38.barattus.util.ListUtils;
import com.mechamic38.barattus.util.Result;
import javafx.beans.property.*;
import javafx.collections.FXCollections;

public class CategoryEditorViewModel implements ICategoryEditorViewModel {

    private final ICategoryService categoryService;
    private final ICategoryRepository categoryRepository;

    private final StringProperty error = new SimpleStringProperty("");
    private final ObjectProperty<Category> category = new SimpleObjectProperty<>();
    private final BooleanProperty canGotoParent = new SimpleBooleanProperty();
    private final ListProperty<Category> subcategories = new SimpleListProperty<>();
    private final ListProperty<CategoryField> fields = new SimpleListProperty<>();

    public CategoryEditorViewModel(ICategoryService categoryService, ICategoryRepository categoryRepository) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;

        category.addListener((observable, oldValue, newValue) -> {
            subcategories.set(FXCollections.observableList(
                    ListUtils.copy(categoryService.getDirectSubcategories(category.get()))
            ));
            fields.set(FXCollections.observableList(
                    ListUtils.copy(category.get().getNativeFields())
            ));
            canGotoParent.set(category.get().getParentName() != null);
        });
    }

    @Override
    public void updateCategory(String description) {
        Result<Category> result = categoryService.updateCategory(category.get(), description);
        if (result.isError()) {
            error.set(result.getError());
        } else {
            category.set(result.getResult());
        }
    }

    @Override
    public void addCategory(String name, String description) {
        Result<Category> result = categoryService.addCategory(category.get(), name, description);
        if (result.isError()) {
            error.set(result.getError());
        } else {
            subcategories.add(result.getResult());
        }
    }

    @Override
    public void addField(String name, CategoryField.Type type, boolean mandatory) {
        Result<CategoryField> result = categoryService.addField(category.get(), name, type, mandatory);
        if (result.isError()) {
            error.set(result.getError());
        } else {
            fields.add(result.getResult());
        }
    }

    @Override
    public void deleteField(CategoryField field) {
        Result<CategoryField> result = categoryService.deleteField(category.get(), field);
        if (result.isError()) {
            error.set(result.getError());
        } else {
            fields.remove(result.getResult());
        }
    }

    @Override
    public void gotoHierarchy() {
        SessionState.getInstance().setCategory(
                categoryRepository.getById(
                        category.get().getHierarchyID()
                )
        );
        category.set(
                SessionState.getInstance().getCategory()
        );
    }

    @Override
    public void gotoParent() {
        if (category.get().getParentName() == null) return;
        SessionState.getInstance().setCategory(
                categoryRepository.getById(
                        category.get().getParentID()
                )
        );
        category.set(
                SessionState.getInstance().getCategory()
        );
    }

    @Override
    public void setCategoryToEdit(Category category) {
        SessionState.getInstance().setCategory(category);
        this.category.set(
                SessionState.getInstance().getCategory()
        );
    }

    @Override
    public void setFieldToEdit(CategoryField field) {
        SessionState.getInstance().setField(field);
    }

    @Override
    public StringProperty errorProperty() {
        return error;
    }

    @Override
    public ObjectProperty<Category> categoryProperty() {
        return category;
    }

    @Override
    public BooleanProperty canGotoParentProperty() {
        return canGotoParent;
    }

    @Override
    public ListProperty<Category> subcategoriesProperty() {
        return subcategories;
    }

    @Override
    public ListProperty<CategoryField> fieldsProperty() {
        return fields;
    }

    @Override
    public void initialize() {
        category.set(
                SessionState.getInstance().getCategory()
        );
    }
}
