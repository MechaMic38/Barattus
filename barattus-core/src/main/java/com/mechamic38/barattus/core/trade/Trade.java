package com.mechamic38.barattus.core.trade;

import com.mechamic38.barattus.core.common.Entity;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

/**
 * Trade between two different users.
 */
public class Trade extends Entity<UUID> {

    private final UUID initiatorUserID;
    private final UUID proposedUserID;
    private final UUID initiatorOfferID;
    private final UUID proposedOfferID;
    private final TradeDetails tradeDetails;
    int editedCount;
    private LocalDateTime lastUpdate;
    private TradeStatus status;


    /**
     * Creates a new trade between two users, using their selected offers.
     *
     * @param uuid             Unique identifier
     * @param initiatorUserID  ID of the user who proposed the trade
     * @param proposedUserID   ID of the user who put his offer available for trade
     * @param initiatorOfferID ID of the offer used to propose the trade
     * @param proposedOfferID  ID of the offer that was put availabe for trade
     */
    public Trade(UUID uuid,
                 UUID initiatorUserID, UUID proposedUserID,
                 UUID initiatorOfferID, UUID proposedOfferID) {
        this(initiatorUserID, proposedUserID, initiatorOfferID, proposedOfferID);
        this.id = uuid;
    }

    /**
     * Creates a new trade between two users, using their selected offers.
     *
     * @param initiatorUserID  ID of the user who proposed the trade
     * @param proposedUserID   ID of the user who put his offer available for trade
     * @param initiatorOfferID ID of the offer used to propose the trade
     * @param proposedOfferID  ID of the offer that was put availabe for trade
     */
    public Trade(UUID initiatorUserID, UUID proposedUserID,
                 UUID initiatorOfferID, UUID proposedOfferID) {
        this.initiatorUserID = initiatorUserID;
        this.proposedUserID = proposedUserID;
        this.initiatorOfferID = initiatorOfferID;
        this.proposedOfferID = proposedOfferID;
        this.editedCount = 0;
        this.tradeDetails = TradeDetails.getDefault();
    }

    /**
     * Determines which user's turn is.
     *
     * @return user ID
     */
    public UUID getEditTurnUser() {
        return (editedCount % 2 == 0) ? proposedUserID : initiatorUserID;
    }

    /**
     * Checks if the given participant belongs to the trade.
     *
     * @param userID user ID
     * @return true/false
     */
    public boolean hasParticipant(UUID userID) {
        return this.initiatorUserID.equals(userID)
                || this.proposedUserID.equals(userID);
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    protected void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public TradeStatus getStatus() {
        return status;
    }

    public void setStatus(TradeStatus status) {
        this.status = status;
    }

    public int getEditedCount() {
        return editedCount;
    }

    public void setEditedCount(int editedCount) {
        this.editedCount = editedCount;
    }

    public UUID getInitiatorUserID() {
        return initiatorUserID;
    }

    public UUID getProposedUserID() {
        return proposedUserID;
    }

    public UUID getInitiatorOfferID() {
        return initiatorOfferID;
    }

    public UUID getProposedOfferID() {
        return proposedOfferID;
    }

    public TradeDetails getTradeDetails() {
        return tradeDetails;
    }

    public void updateTradeDetails(String place, DayOfWeek day, LocalTime time) {
        tradeDetails.update(place, day, time);
        lastUpdate = LocalDateTime.now();
    }
}
