package com.mechamic38.barattus.gui.category;

import com.mechamic38.barattus.core.category.Category;
import com.mechamic38.barattus.core.category.CategoryField;
import com.mechamic38.barattus.gui.common.ViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

public interface ICategoryEditorViewModel extends ViewModel {

    void updateCategory(String description);

    void addCategory(String name, String description);

    void addField(String name, CategoryField.Type type, boolean mandatory);

    void deleteField(CategoryField field);

    void gotoHierarchy();

    void gotoParent();

    void setCategoryToEdit(Category category);

    void setFieldToEdit(CategoryField field);

    StringProperty errorProperty();

    ObjectProperty<Category> categoryProperty();

    BooleanProperty canGotoParentProperty();

    ListProperty<Category> subcategoriesProperty();

    ListProperty<CategoryField> fieldsProperty();
}
