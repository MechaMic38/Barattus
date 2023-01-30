package com.mechamic38.barattus.gui.category;

import com.mechamic38.barattus.core.category.Category;
import com.mechamic38.barattus.core.category.CategoryField;
import com.mechamic38.barattus.gui.common.ViewModel;

public interface ICategoryEditorVIewModel extends ViewModel {

    void updateCategory(String description);

    void addCategory(String name, String description);

    void addField(String name, CategoryField.Type type, boolean mandatory);

    void deleteField(CategoryField field);

    void setCategoryToEdit(String category);

    void setCategoryToEdit(Category category);

    void setFieldToEdit(CategoryField field);
}
