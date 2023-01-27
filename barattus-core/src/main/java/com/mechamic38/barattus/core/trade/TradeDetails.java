package com.mechamic38.barattus.core.trade;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Holds the trade details agreed by the participants.
 */
public class TradeDetails {

    String place;
    DayOfWeek day;
    LocalTime time;


    /**
     * Creates a new trade details object with the given place, day and time of the trade.
     *
     * @param place Place of the trade
     * @param day   Day of the trade
     * @param time  Time of the trade
     */
    public TradeDetails(String place, DayOfWeek day, LocalTime time) {
        this.place = place;
        this.day = day;
        this.time = time;
    }

    /**
     * Returns an empty trade details object.
     *
     * @return Empty trade details object
     */
    public static TradeDetails getDefault() {
        return new TradeDetails("", null, null);
    }

    /**
     * Updates the trade details with new data.
     *
     * @param place Updated place
     * @param day   Updated day
     * @param time  Updated time
     */
    public void update(String place, DayOfWeek day, LocalTime time) {
        this.place = place;
        this.day = day;
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    protected boolean equalsCore(TradeDetails other) {
        return place.equals(other.place)
                && day.equals(other.day)
                && time.equals(other.time);
    }

    protected int hashCodeCore() {
        return Objects.hash(place, day, time);
    }
}
