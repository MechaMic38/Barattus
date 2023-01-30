package com.mechamic38.barattus.gui.category;

import com.mechamic38.barattus.core.category.Category;
import com.mechamic38.barattus.gui.common.ViewModel;

public interface ICategoryListViewModel extends ViewModel {

    void addHierarchy(String name, String description);

    void updateCategoryList(Category hierarchy);

    void setCategoryToEdit(Category category);
}
