package com.mechamic38.barattus.gui.category;

import com.mechamic38.barattus.core.category.Category;
import com.mechamic38.barattus.core.category.ICategoryRepository;
import com.mechamic38.barattus.core.category.ICategoryService;

public class CategoryListViewModel implements ICategoryListViewModel {

    private final ICategoryService categoryService;
    private final ICategoryRepository categoryRepository;

    public CategoryListViewModel(ICategoryService categoryService, ICategoryRepository categoryRepository) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void addHierarchy(String name, String description) {

    }

    @Override
    public void updateCategoryList(Category hierarchy) {

    }

    @Override
    public void setCategoryToEdit(Category category) {

    }
}
