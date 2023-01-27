package com.mechamic38.barattus.core.tradeparams;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public record HourInterval(LocalTime startTime, LocalTime endTime) {

    public HourInterval {
        if (startTime.isAfter(endTime))
            throw new IllegalArgumentException("Start time cannot be after the end time");

    }

    /**
     * Checks if this hour interval overlaps with the given interval.
     *
     * @param interval hour interval
     * @return true if overlapping, else false
     */
    public boolean overlapsInterval(HourInterval interval) {
        if (startTime.isAfter(interval.startTime()) && startTime.isBefore(interval.endTime())) return true;
        if (endTime.isAfter(interval.startTime()) && endTime.isBefore(interval.endTime())) return true;

        if (interval.startTime().isAfter(startTime) && interval.startTime().isBefore(endTime)) return true;
        if (interval.endTime().isAfter(startTime) && interval.endTime().isBefore(endTime)) return true;

        return false;
    }

    /**
     * Gets a list of half and full hours inside the time interval.
     *
     * @return list of half and full hours
     */
    public List<LocalTime> getHalfAndFullTimes() {
        List<LocalTime> times = new LinkedList<>();
        LocalTime firstAvailableTime = getFirstHalfOrFullTime(startTime);

        while (firstAvailableTime.isBefore(endTime) || firstAvailableTime.equals(endTime)) {
            times.add(firstAvailableTime);
            firstAvailableTime = firstAvailableTime.plus(30, ChronoUnit.MINUTES);
        }

        return times;
    }

    /**
     * Gets the first available half or full hour.
     *
     * @param startTime Start time
     * @return First available time
     */
    private LocalTime getFirstHalfOrFullTime(LocalTime startTime) {
        if (startTime.getMinute() > 30) {
            return LocalTime.of(startTime.getHour() + 1, 0);
        } else if (startTime.getMinute() > 0) {
            return LocalTime.of(startTime.getHour(), 30);
        } else {
            return startTime;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (HourInterval) obj;
        return Objects.equals(this.startTime, that.startTime) &&
                Objects.equals(this.endTime, that.endTime);
    }

    @Override
    public String toString() {
        return "HourInterval[" +
                "startTime=" + startTime + ", " +
                "endTime=" + endTime + ']';
    }

}
