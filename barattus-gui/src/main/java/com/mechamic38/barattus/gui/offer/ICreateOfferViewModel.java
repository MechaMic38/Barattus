package com.mechamic38.barattus.gui.offer;

import com.mechamic38.barattus.core.category.Category;
import com.mechamic38.barattus.core.category.CategoryField;
import com.mechamic38.barattus.gui.common.ViewModel;
import javafx.beans.property.ListProperty;
import javafx.beans.property.StringProperty;

import java.util.HashMap;

public interface ICreateOfferViewModel extends ViewModel {
    void loadSubcategories(Category category);

    void loadCategoryFields(Category category);

    boolean publishOffer(Category category, String offerTitle, HashMap<CategoryField, String> fields);

    StringProperty errorProperty();

    ListProperty<Category> rootCategoriesProperty();

    ListProperty<Category> leafCategoriesProperty();

    ListProperty<CategoryField> fieldsProperty();
}
