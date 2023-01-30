package com.mechamic38.barattus.gui.category;

import com.mechamic38.barattus.core.category.CategoryField;
import com.mechamic38.barattus.gui.common.ViewModel;

public interface IFieldEditorViewModel extends ViewModel {

    void updateField(String newName, CategoryField.Type type, boolean mandatory);
}
