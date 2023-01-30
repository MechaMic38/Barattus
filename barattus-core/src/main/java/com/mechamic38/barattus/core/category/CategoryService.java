package com.mechamic38.barattus.core.category;

public class CategoryService implements ICategoryService {

    private final ICategoryRepository repository;

    public CategoryService(ICategoryRepository repository) {
        this.repository = repository;
    }
}
