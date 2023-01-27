package com.mechamic38.barattus.persistence.offer;

import com.google.gson.annotations.SerializedName;

/**
 * Offer log data transfer object
 */
public class OfferLogDTO {

    @SerializedName("id")
    private final String id;
    @SerializedName("time")
    private final String time;
    @SerializedName("offerID")
    private final String offerID;
    @SerializedName("status")
    private final String status;


    public OfferLogDTO(String id, String time, String offerID, String status) {
        this.id = id;
        this.time = time;
        this.offerID = offerID;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getOfferID() {
        return offerID;
    }

    public String getStatus() {
        return status;
    }
}
