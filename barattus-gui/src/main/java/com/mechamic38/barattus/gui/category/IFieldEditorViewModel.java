package com.mechamic38.barattus.gui.category;

import com.mechamic38.barattus.core.category.CategoryField;
import com.mechamic38.barattus.gui.common.ViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

public interface IFieldEditorViewModel extends ViewModel {

    boolean updateField(String newName, CategoryField.Type type, boolean mandatory);

    StringProperty errorProperty();

    ObjectProperty<CategoryField> fieldProperty();
}
