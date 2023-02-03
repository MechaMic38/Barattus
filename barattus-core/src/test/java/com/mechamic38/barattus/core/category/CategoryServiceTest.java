package com.mechamic38.barattus.core.category;

import com.mechamic38.barattus.persistence.category.ICategoryDataSource;
import com.mechamic38.barattus.util.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class CategoryServiceTest {

    ICategoryDataSource categoryDataSource;
    ICategoryRepository categoryRepository;
    ICategoryService categoryService;

    HashMap<String, Category> categories;

    @BeforeEach
    public void init() {
        categoryDataSource = new CategoryDataSourceMock();
        categoryRepository = new CategoryRepositoryMock();
        categoryService = new CategoryService(categoryRepository, categoryDataSource, new CategoryMapper());

        categories = getCategories();
        categoryRepository.importData(categories.values().stream().toList());
    }

    @Test
    void should_add_hierarchy() {
        Result<Category> result = categoryService.addHierarchy("Simracing", "Articoli per simracing");

        Assertions.assertFalse(result.isError());

        Category category = result.getResult();
        Assertions.assertNotNull(categoryRepository.getById(category.getID()));
    }

    @Test
    void should_not_add_hierarchy_if_same_name_exists() {
        Result<Category> result = categoryService.addHierarchy("Sport", "Articoli per lo sport");

        Assertions.assertTrue(result.isError());
    }

    @Test
    void should_add_category() {
        Category parent = categoryRepository.getById(
                categories.get("Sport").getID()
        );
        Result<Category> result = categoryService.addCategory(parent, "Postazioni", "Postazioni da simracing");

        Assertions.assertFalse(result.isError());

        Category category = result.getResult();
        Assertions.assertNotNull(categoryRepository.getById(category.getID()));
    }

    @Test
    void should_not_add_category_if_same_name_exists_within_hierarchy() {
        Category parent = categoryRepository.getById(
                categories.get("Ciclismo").getID()
        );
        Result<Category> result = categoryService.addCategory(parent, "Sport", "Articoli per lo sport");

        Assertions.assertTrue(result.isError());
    }

    @Test
    void should_add_field() {
        Category category = categoryRepository.getById(
                categories.get("Abbigliamento maschile").getID()
        );
        CategoryField field = new CategoryField("Taglia", CategoryField.Type.STRING, false);
        Result<CategoryField> result = categoryService.addField(
                category,
                field.getName(),
                field.getFieldType(),
                field.getMandatory());

        Assertions.assertFalse(result.isError());

        category = categoryRepository.getById(
                categories.get("Abbigliamento maschile").getID()
        );
        Assertions.assertTrue(category.getNativeFields().contains(field));
    }

    @Test
    void should_not_add_field_if_exists_within_ascendants() {
        Category category = categoryRepository.getById(
                categories.get("Abbigliamento maschile").getID()
        );
        CategoryField field = new CategoryField("Stato di conservazione", CategoryField.Type.STRING, false);
        Result<CategoryField> result = categoryService.addField(
                category,
                field.getName(),
                field.getFieldType(),
                field.getMandatory()
        );

        Assertions.assertTrue(result.isError());

        category = categoryRepository.getById(
                categories.get("Abbigliamento maschile").getID()
        );
        Assertions.assertFalse(category.getNativeFields().contains(field));
    }

    @Test
    void should_not_add_field_if_exists_within_descendants() {
        Category category = categoryRepository.getById(
                categories.get("Abbigliamento maschile").getID()
        );
        CategoryField field = new CategoryField("Taglia", CategoryField.Type.STRING, true);
        Result<CategoryField> result = categoryService.addField(
                category,
                field.getName(),
                field.getFieldType(),
                field.getMandatory()
        );

        category = categoryRepository.getById(
                categories.get("Abbigliamento").getID()
        );
        result = categoryService.addField(
                category,
                field.getName(),
                field.getFieldType(),
                field.getMandatory()
        );

        Assertions.assertTrue(result.isError());

        category = categoryRepository.getById(
                categories.get("Abbigliamento").getID()
        );
        Assertions.assertFalse(category.getNativeFields().contains(field));
    }

    private HashMap<String, Category> getCategories() {
        HashMap<String, Category> categories = new LinkedHashMap<>();

        // Base hierarchy fields ==========================

        List<CategoryField> baseFields = new LinkedList<>();
        baseFields.add(new CategoryField(
                "Stato di conservazione",
                CategoryField.Type.STRING,
                true
        ));
        baseFields.add(new CategoryField(
                "Descrizione libera",
                CategoryField.Type.STRING,
                false
        ));

        //Hierarchies ==========================

        Category category = new Category(
                "Sport",
                "Sport",
                null,
                "Articoli per lo sport"
        );
        baseFields.forEach(category::addNativeField);
        categories.put("Sport", category);

        category = new Category(
                "Abbigliamento",
                "Abbigliamento",
                null,
                "Articoli di abbigliamento"
        );
        baseFields.forEach(category::addNativeField);
        categories.put("Abbigliamento", category);

        //Subcategories ==========================

        category = new Category(
                "Sport",
                "Ciclismo",
                "Sport",
                "Articoli per il ciclismo"
        );
        categories.put("Ciclismo", category);

        category = new Category(
                "Sport",
                "Trekking",
                "Sport",
                "Articoli per il trekking"
        );
        categories.put("Trekking", category);

        category = new Category(
                "Abbigliamento",
                "Abbigliamento maschile",
                "Abbigliamento",
                "Articoli di abbigliamento maschile"
        );
        categories.put("Abbigliamento maschile", category);

        category = new Category(
                "Abbigliamento",
                "Abbigliamento femminile",
                "Abbigliamento",
                "Articoli di abbigliamento femminile"
        );
        categories.put("Abbigliamento femminile", category);

        return categories;
    }


}
