package com.mechamic38.barattus.core.category;

import java.util.LinkedList;
import java.util.List;

public class CategoryRepositoryMock implements ICategoryRepository {

    private final List<Category> categories;

    public CategoryRepositoryMock() {
        this.categories = new LinkedList<>();
    }

    @Override
    public Category getById(String id) {
        return categories.stream()
                .filter(category -> category.checkID(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Category> getAll() {
        return categories;
    }

    @Override
    public void save(Category category) {
        if (!this.categories.contains(category)) {
            this.categories.add(category);
        }
    }

    @Override
    public void loadFromDataSource() {

    }

    @Override
    public void importData(List<Category> categories) {
        this.categories.clear();
        this.categories.addAll(categories);
    }
}
