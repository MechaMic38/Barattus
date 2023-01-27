package com.mechamic38.barattus.persistence.trade;

import com.google.gson.annotations.SerializedName;

/**
 * Trade data transfer object.
 */
public class TradeDTO {

    @SerializedName("id")
    private final String id;
    @SerializedName("lastUpdate")
    private final String lastUpdate;
    @SerializedName("status")
    private final String status;
    @SerializedName("editedCount")
    private final int editedCount;

    @SerializedName("initiatorUserID")
    private final String initiatorUserID;
    @SerializedName("proposedUserID")
    private final String proposedUserID;
    @SerializedName("initiatorOfferID")
    private final String initiatorOfferID;
    @SerializedName("proposedOfferID")
    private final String proposedOfferID;

    @SerializedName("place")
    private final String place;
    @SerializedName("day")
    private final String day;
    @SerializedName("time")
    private final String time;


    public TradeDTO(String id, String lastUpdate, String status,
                    int editedCount, String initiatorUserID, String initiatorOfferID,
                    String proposedUserID, String proposedOfferID, String place,
                    String day, String time) {
        this.id = id;
        this.lastUpdate = lastUpdate;
        this.status = status;
        this.editedCount = editedCount;
        this.initiatorUserID = initiatorUserID;
        this.proposedUserID = proposedUserID;
        this.initiatorOfferID = initiatorOfferID;
        this.proposedOfferID = proposedOfferID;
        this.place = place;
        this.day = day;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public String getStatus() {
        return status;
    }

    public int getEditedCount() {
        return editedCount;
    }

    public String getInitiatorUserID() {
        return initiatorUserID;
    }

    public String getProposedUserID() {
        return proposedUserID;
    }

    public String getInitiatorOfferID() {
        return initiatorOfferID;
    }

    public String getProposedOfferID() {
        return proposedOfferID;
    }

    public String getPlace() {
        return place;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }
}
