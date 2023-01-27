package com.mechamic38.barattus.persistence.category;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Category Data Transfer Object
 */
public class CategoryDTO {

    @SerializedName("id")
    private final String id;
    @SerializedName("hierarchyName")
    private final String hierarchyName;
    @SerializedName("parentName")
    private final String parentName;
    @SerializedName("categoryName")
    private final String categoryName;
    @SerializedName("description")
    private final String description;
    @SerializedName("nativeFields")
    private final List<CategoryFieldDTO> nativeFields;


    public CategoryDTO(String id, String hierarchyName, String parentName,
                       String categoryName, String description, List<CategoryFieldDTO> nativeFields) {
        this.id = id;
        this.hierarchyName = hierarchyName;
        this.parentName = parentName;
        this.categoryName = categoryName;
        this.description = description;
        this.nativeFields = nativeFields;
    }

    public String getId() {
        return id;
    }

    public String getHierarchyName() {
        return hierarchyName;
    }

    public String getParentName() {
        return parentName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getDescription() {
        return description;
    }

    public List<CategoryFieldDTO> getNativeFields() {
        return nativeFields;
    }
}
