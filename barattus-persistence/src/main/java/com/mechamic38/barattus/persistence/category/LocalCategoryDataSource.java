package com.mechamic38.barattus.persistence.category;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.mechamic38.barattus.persistence.common.InvalidFileException;
import com.mechamic38.barattus.persistence.common.LocalDataSource;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * Gateway that manages the category data into the in-memory hierarchies file.
 */
public class LocalCategoryDataSource extends LocalDataSource implements ICategoryDataSource {

    private static final File DATA = new File(
            System.getProperty("barattus.dir.default.path"),
            "category-data.json"
    );
    private static final String KEY = "categories";


    /**
     * Creates the local category storage gateway.
     */
    public LocalCategoryDataSource() {
        super();
        createLocalFile(DATA);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CategoryDTO> getAll() {
        JsonObject json;
        List<CategoryDTO> categories;

        try {
            json = load(DATA, KEY);
            categories = extractFromJson(json);
        } catch (InvalidFileException e) {
            categories = getDefaultCategories();
        }

        return categories;
    }

    @Override
    public List<CategoryDTO> getAll(String filePath) throws InvalidFileException {
        JsonObject json = loadFromExternalFile(filePath);
        List<CategoryDTO> categories = extractFromJson(json);

        uploadToFile(json, DATA);
        return categories;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insert(CategoryDTO categoryDTO) {
        JsonObject json = load(DATA, KEY);
        JsonArray categories = json.getAsJsonArray(KEY);
        //System.out.println("Categories during save:");
        //System.out.println(categories);

        JsonElement jsonCategory = gson.toJsonTree(categoryDTO);
        categories.add(jsonCategory);

        return uploadToFile(json, DATA);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(CategoryDTO categoryDTO) {
        JsonObject json = load(DATA, KEY);
        JsonArray categories = json.getAsJsonArray(KEY);
        //System.out.println("Categories during save:");
        //System.out.println(categories);

        JsonElement oldJsonCategory = getCategoryFromJson(
                categories,
                categoryDTO.getHierarchyName(),
                categoryDTO.getCategoryName()
        );
        JsonElement updatedJsonCategory = gson.toJsonTree(categoryDTO);

        categories.remove(oldJsonCategory);
        categories.add(updatedJsonCategory);

        return uploadToFile(json, DATA);
    }

    private List<CategoryDTO> extractFromJson(JsonObject json) throws InvalidFileException {
        JsonArray categoriesJson = json.getAsJsonArray(KEY);
        if (categoriesJson == null) throw new InvalidFileException();

        try {
            Type categoryListType = new TypeToken<List<CategoryDTO>>() {
            }.getType();
            return gson.fromJson(
                    categoriesJson,
                    categoryListType
            );
        } catch (JsonSyntaxException e) {
            throw new InvalidFileException();
        }
    }

    private List<CategoryDTO> getDefaultCategories() {
        return Collections.emptyList();
    }

    /**
     * Extracts the json Category from the categories JsonArray, based on the given hierarchyName and categoryName.
     *
     * @param categories    JsonArray of categories
     * @param hierarchyName Name of the hierarchy
     * @param categoryName  Name of the category
     * @return JsonElement category that matches
     */
    private JsonElement getCategoryFromJson(JsonArray categories, String hierarchyName, String categoryName) {
        for (JsonElement categoryJson : categories) {
            CategoryDTO category = gson.fromJson(categoryJson, CategoryDTO.class);
            if (category.getHierarchyName().equals(hierarchyName) && category.getCategoryName().equals(categoryName)) {
                return categoryJson;
            }
        }

        return null;
    }
}
