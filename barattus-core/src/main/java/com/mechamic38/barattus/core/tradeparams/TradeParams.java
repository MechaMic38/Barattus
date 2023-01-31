package com.mechamic38.barattus.core.tradeparams;

import java.time.DayOfWeek;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Trade parameters of the application.
 */
public class TradeParams {

    private static final int MIN_EXPIRY_DAYS = 1;

    private final List<String> places;
    private final List<DayOfWeek> days;
    private final List<HourInterval> hourIntervals;
    private String square;
    private int expirationDays;

    private boolean squareSet = false;


    public TradeParams(String square, int expirationDays) {
        this.setSquare(square);
        this.setExpirationDays(expirationDays);
        this.places = new LinkedList<>();
        this.days = new LinkedList<>();
        this.hourIntervals = new LinkedList<>();
    }

    TradeParams(String square, int expirationDays,
                List<String> places, List<DayOfWeek> days, List<HourInterval> hourIntervals) {
        this.setSquare(square);
        this.setExpirationDays(expirationDays);
        this.places = places;
        this.days = days;
        this.hourIntervals = hourIntervals;
    }

    public static TradeParams getDefaultParams() {
        return new TradeParams("", 30);
    }

    public String getSquare() {
        return square;
    }

    /**
     * Sets the square where the trades will happen.
     * An empty square means there is no square currently set.
     * The square can be modified only once, trying to change it afterwards
     * will raise an exception.
     *
     * @param square square for trades
     * @throws IllegalArgumentException if a non-empty square has already been set
     */
    public void setSquare(String square) {
        if (square.equals(this.square)) return;

        if (isSquareSet() && !square.equals(this.square))
            throw new IllegalArgumentException("trade.params.error.square.set");

        this.square = square;
        if (!Objects.isNull(square) && !square.isBlank()) squareSet = true;
    }

    public List<String> getPlaces() {
        return places;
    }

    /**
     * Adds a place for trades, if not already present.
     *
     * @param place place for trades
     * @throws IllegalArgumentException if the place is blank or already exists
     */
    public void addPlace(String place) {
        if (place.isBlank())
            throw new IllegalArgumentException("trade.params.error.place.empty");

        if (places.contains(place))
            throw new IllegalArgumentException("trade.params.error.place.exists");

        places.add(place);
    }

    /**
     * Removes the selected place for trades, if present.
     *
     * @param place place for trades
     */
    public void removePlace(String place) {
        places.remove(place);
    }

    public List<DayOfWeek> getDays() {
        return days;
    }

    /**
     * Adds a day for trades, if not already present.
     *
     * @param day day for trades
     * @throws IllegalArgumentException if the day already exists
     */
    public void addDay(DayOfWeek day) {
        if (days.contains(day))
            throw new IllegalArgumentException("trade.params.error.day.exists");

        days.add(day);
    }

    /**
     * Removes the selected day for trades, if present.
     *
     * @param day day for trades
     */
    public void removeDay(DayOfWeek day) {
        days.remove(day);
    }

    public List<HourInterval> getHourIntervals() {
        return hourIntervals;
    }

    /**
     * Adds an hour interval for trades, if not already present.
     *
     * @param interval hour interval for trades
     * @throws IllegalArgumentException if the hour interval already exists
     */
    public void addHourInterval(HourInterval interval) {
        if (hourIntervals.contains(interval))
            throw new IllegalArgumentException("trade.params.error.interval.exists");

        if (overlapsAnyInterval(interval))
            throw new IllegalArgumentException("trade.params.error.interval.overlap");

        if (interval.getHalfAndFullTimes().isEmpty())
            throw new IllegalArgumentException("trade.params.error.interval.invalid");

        hourIntervals.add(interval);
    }

    /**
     * Removes the selected hour interval for trades, if present.
     *
     * @param interval hour interval for trades
     */
    public void removeHourInterval(HourInterval interval) {
        hourIntervals.remove(interval);
    }

    public int getExpirationDays() {
        return expirationDays;
    }

    /**
     * Sets the expiration days for trades.
     *
     * @param expirationDays expiration days for trades
     * @throws IllegalArgumentException if the expiration days are less than 1
     */
    public void setExpirationDays(int expirationDays) {
        if (expirationDays < MIN_EXPIRY_DAYS)
            throw new IllegalArgumentException("trade.params.error.expiration.invalid");

        this.expirationDays = expirationDays;
    }

    public boolean isSquareSet() {
        return squareSet;
    }

    private boolean overlapsAnyInterval(HourInterval interval) {
        for (HourInterval hourInterval : hourIntervals) {
            if (interval.overlapsInterval(hourInterval)) return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TradeParams that = (TradeParams) o;
        return expirationDays == that.expirationDays && Objects.equals(places, that.places) && Objects.equals(days, that.days) && Objects.equals(hourIntervals, that.hourIntervals) && Objects.equals(square, that.square);
    }

    @Override
    public int hashCode() {
        return Objects.hash(places, days, hourIntervals, square, expirationDays);
    }
}
