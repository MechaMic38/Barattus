package com.mechamic38.barattus.persistence.category;

import com.google.gson.annotations.SerializedName;

/**
 * Category Field Data Transfer Object
 */
public class CategoryFieldDTO {

    @SerializedName("name")
    private final String name;
    @SerializedName("fieldType")
    private final String fieldType;
    @SerializedName("isMandatory")
    private final boolean mandatory;


    public CategoryFieldDTO(String name, String fieldType, boolean mandatory) {
        this.name = name;
        this.fieldType = fieldType;
        this.mandatory = mandatory;
    }

    public String getName() {
        return name;
    }

    public String getFieldType() {
        return fieldType;
    }

    public boolean isMandatory() {
        return mandatory;
    }
}
