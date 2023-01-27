package com.mechamic38.barattus.persistence.offer;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Offer data transfer object
 */
public class OfferDTO {

    @SerializedName("id")
    private final String id;
    @SerializedName("title")
    private final String title;
    @SerializedName("creationDate")
    private final String creationDate;
    @SerializedName("categoryID")
    private final String categoryID;
    @SerializedName("userID")
    private final String userID;
    @SerializedName("status")
    private final String status;
    @SerializedName("offerFields")
    private final List<OfferFieldDTO> offerFields;

    public OfferDTO(String id,
                    String title,
                    String creationDate,
                    String categoryID,
                    String userID,
                    String status,
                    List<OfferFieldDTO> offerFields) {
        this.id = id;
        this.title = title;
        this.creationDate = creationDate;
        this.categoryID = categoryID;
        this.userID = userID;
        this.status = status;
        this.offerFields = offerFields;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public String getUserID() {
        return userID;
    }

    public String getStatus() {
        return status;
    }

    public List<OfferFieldDTO> getOfferFields() {
        return offerFields;
    }
}
