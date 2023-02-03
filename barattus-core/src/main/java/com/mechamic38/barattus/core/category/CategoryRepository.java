package com.mechamic38.barattus.core.category;

import com.mechamic38.barattus.persistence.category.CategoryDTO;
import com.mechamic38.barattus.persistence.category.ICategoryDataSource;

import java.util.LinkedList;
import java.util.List;

/**
 * Categories repository.
 */
public class CategoryRepository implements ICategoryRepository {

    private final List<Category> categories;
    private final ICategoryDataSource dataSource;
    private final CategoryMapper mapper;


    public CategoryRepository(ICategoryDataSource dataSource, CategoryMapper mapper) {
        this.dataSource = dataSource;
        this.mapper = mapper;
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
            dataSource.insert(mapper.toDto(category));
        } else {
            dataSource.update(mapper.toDto(category));
        }
    }

    @Override
    public void loadFromDataSource() {
        for (CategoryDTO category : dataSource.getAll()) {
            categories.add(mapper.fromDto(category));
        }
    }

    @Override
    public void importData(List<Category> categories) {
        this.categories.clear();
        this.categories.addAll(categories);
    }
}
