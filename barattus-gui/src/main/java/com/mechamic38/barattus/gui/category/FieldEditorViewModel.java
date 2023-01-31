package com.mechamic38.barattus.gui.category;

import com.mechamic38.barattus.core.category.CategoryField;
import com.mechamic38.barattus.core.category.ICategoryService;
import com.mechamic38.barattus.gui.common.SessionState;
import com.mechamic38.barattus.util.Result;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FieldEditorViewModel implements IFieldEditorViewModel {

    private final ICategoryService categoryService;

    private final StringProperty error = new SimpleStringProperty("");
    private final ObjectProperty<CategoryField> field = new SimpleObjectProperty<>();

    public FieldEditorViewModel(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public boolean updateField(String newName, CategoryField.Type type, boolean mandatory) {
        Result<CategoryField> result = categoryService.updateField(
                SessionState.getInstance().getCategory(),
                SessionState.getInstance().getField(),
                newName,
                type,
                mandatory
        );
        if (result.isError()) {
            error.set(result.getError());
            return false;
        } else {
            field.set(result.getResult());
            return true;
        }
    }

    @Override
    public void initialize() {
        field.set(
                SessionState.getInstance().getField()
        );
    }

    @Override
    public StringProperty errorProperty() {
        return error;
    }

    @Override
    public ObjectProperty<CategoryField> fieldProperty() {
        return field;
    }
}
