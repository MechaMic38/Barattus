package com.mechamic38.barattus.core.category;

import com.mechamic38.barattus.persistence.category.CategoryDTO;
import com.mechamic38.barattus.persistence.category.ICategoryDataSource;
import com.mechamic38.barattus.persistence.common.InvalidFileException;

import java.util.LinkedList;
import java.util.List;

public class CategoryDataSourceMock implements ICategoryDataSource {

    private final List<CategoryDTO> categories;

    public CategoryDataSourceMock() {
        this.categories = getCategories();
    }

    private List<CategoryDTO> getCategories() {
        List<CategoryDTO> categories = new LinkedList<>();

        categories.add(new CategoryDTO(
                "Sport.Bici",
                "Sport",
                "Sport",
                "Bici",
                "Biciclette",
                new LinkedList<>()
        ));

        categories.add(new CategoryDTO(
                "Sport.Sport",
                "Sport",
                null,
                "Sport",
                "Articoli per lo sport",
                new LinkedList<>()
        ));

        return categories;
    }

    @Override
    public List<CategoryDTO> getAll(String filePath) throws InvalidFileException {
        return categories;
    }

    @Override
    public boolean insert(CategoryDTO entity) {
        return false;
    }

    @Override
    public boolean update(CategoryDTO entity) {
        return false;
    }

    @Override
    public List<CategoryDTO> getAll() {
        return categories;
    }
}
