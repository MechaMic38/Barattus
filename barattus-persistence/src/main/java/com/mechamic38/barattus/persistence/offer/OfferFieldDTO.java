package com.mechamic38.barattus.persistence.offer;

import com.google.gson.annotations.SerializedName;

/**
 * Offer field data transfer object
 */
public class OfferFieldDTO {

    @SerializedName("name")
    private final String name;
    @SerializedName("type")
    private final String type;
    @SerializedName("content")
    private final String content;


    public OfferFieldDTO(String name, String type, String content) {
        this.name = name;
        this.type = type;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}
