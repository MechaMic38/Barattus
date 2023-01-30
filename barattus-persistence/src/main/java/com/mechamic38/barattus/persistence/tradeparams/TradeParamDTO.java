package com.mechamic38.barattus.persistence.tradeparams;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Trade params data transfer object
 */
public final class TradeParamDTO {
    @SerializedName("square")
    private final String square;
    @SerializedName("places")
    private final List<String> places;
    @SerializedName("days")
    private final List<String> days;
    @SerializedName("hourIntervals")
    private final List<String> hourIntervals;
    @SerializedName("expirationDays")
    private final int expirationDays;

    public TradeParamDTO(String square,
                         List<String> places,
                         List<String> days,
                         List<String> hourIntervals,
                         int expirationDays) {
        this.square = square;
        this.places = places;
        this.days = days;
        this.hourIntervals = hourIntervals;
        this.expirationDays = expirationDays;
    }

    public String getSquare() {
        return square;
    }

    public List<String> getPlaces() {
        return places;
    }

    public List<String> getDays() {
        return days;
    }

    public List<String> getHourIntervals() {
        return hourIntervals;
    }

    public int getExpirationDays() {
        return expirationDays;
    }

}
