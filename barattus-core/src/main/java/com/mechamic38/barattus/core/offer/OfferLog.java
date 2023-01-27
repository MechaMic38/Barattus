package com.mechamic38.barattus.core.offer;

import com.mechamic38.barattus.core.common.Entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Offer status log.
 */
public final class OfferLog extends Entity<UUID> {

    private final LocalDateTime time;
    private final UUID offerID;
    private final OfferStatus status;


    /**
     * Creates a new offer log with the given time, offerID and status.
     *
     * @param uuid    Unique identifier
     * @param time    Time of the log
     * @param offerID OfferID the log refers to
     * @param status  Status of the offer at the time of the log
     */
    public OfferLog(UUID uuid, LocalDateTime time, UUID offerID, OfferStatus status) {
        this(time, offerID, status);
        this.id = uuid;
    }

    /**
     * Creates a new offer log with the given time, offerID and status.
     *
     * @param time    Time of the log
     * @param offerID OfferID the log refers to
     * @param status  Status of the offer at the time of the log
     */
    public OfferLog(LocalDateTime time, UUID offerID, OfferStatus status) {
        this.time = time;
        this.offerID = offerID;
        this.status = status;
    }

    public LocalDateTime time() {
        return time;
    }

    public UUID offerID() {
        return offerID;
    }

    public OfferStatus status() {
        return status;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (OfferLog) obj;
        return Objects.equals(this.time, that.time) &&
                Objects.equals(this.offerID, that.offerID) &&
                Objects.equals(this.status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, offerID, status);
    }

    @Override
    public String toString() {
        return "OfferLog[" +
                "time=" + time + ", " +
                "offerID=" + offerID + ", " +
                "status=" + status + ']';
    }

}
