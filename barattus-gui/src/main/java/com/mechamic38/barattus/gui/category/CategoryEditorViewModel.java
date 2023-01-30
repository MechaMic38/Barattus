package com.mechamic38.barattus.gui.category;

import com.mechamic38.barattus.core.category.Category;
import com.mechamic38.barattus.core.category.CategoryField;
import com.mechamic38.barattus.core.category.ICategoryRepository;
import com.mechamic38.barattus.core.category.ICategoryService;

public class CategoryEditorViewModel implements ICategoryEditorVIewModel {

    private final ICategoryService categoryService;
    private final ICategoryRepository categoryRepository;

    public CategoryEditorViewModel(ICategoryService categoryService, ICategoryRepository categoryRepository) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void updateCategory(String description) {

    }

    @Override
    public void addCategory(String name, String description) {

    }

    @Override
    public void addField(String name, CategoryField.Type type, boolean mandatory) {

    }

    @Override
    public void deleteField(CategoryField field) {

    }

    @Override
    public void setCategoryToEdit(String category) {

    }

    @Override
    public void setCategoryToEdit(Category category) {

    }

    @Override
    public void setFieldToEdit(CategoryField field) {

    }
}
