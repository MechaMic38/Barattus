package com.mechamic38.barattus.gui.category;

import com.mechamic38.barattus.core.category.CategoryField;
import com.mechamic38.barattus.core.category.ICategoryService;

public class FieldEditorViewModel implements IFieldEditorViewModel {

    private final ICategoryService categoryService;

    public FieldEditorViewModel(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public void updateField(String newName, CategoryField.Type type, boolean mandatory) {

    }
}
