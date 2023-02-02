package com.mechamic38.barattus.core.category;

import com.mechamic38.barattus.util.Result;

import java.util.List;

public interface ICategoryService {
    Result<Category> addHierarchy(String name, String description);

    Result<Category> addCategory(Category category, String name, String description);

    Result<Category> updateCategory(Category category, String description);

    Result<CategoryField> addField(Category category, String name, CategoryField.Type type, boolean mandatory);

    Result<CategoryField> updateField(Category category, CategoryField field, String name, CategoryField.Type type, boolean mandatory);

    Result<CategoryField> deleteField(Category category, CategoryField field);

    List<Category> getHierarchies();

    List<Category> getSubcategories(Category category);

    List<Category> getDirectSubcategories(Category category);

    List<Category> getLeafCategories(Category category);

    List<CategoryField> getCategoryFields(Category category);

    Result<List<Category>> loadParamsFromFile(String path);
}
