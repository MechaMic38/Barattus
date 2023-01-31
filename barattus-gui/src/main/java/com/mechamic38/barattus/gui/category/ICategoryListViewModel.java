package com.mechamic38.barattus.gui.category;

import com.mechamic38.barattus.core.category.Category;
import com.mechamic38.barattus.gui.common.ViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.StringProperty;

public interface ICategoryListViewModel extends ViewModel {

    void addHierarchy(String name, String description);

    void updateCategoryList(Category hierarchy);

    void setCategoryToEdit(Category category);

    BooleanProperty adminProperty();

    StringProperty errorProperty();

    ListProperty<Category> rootCategoriesProperty();

    ListProperty<Category> leafCategoriesProperty();
}
