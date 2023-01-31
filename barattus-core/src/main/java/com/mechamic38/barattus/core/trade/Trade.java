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

    private final String initiatorUserID;
    private final String proposedUserID;
    private final UUID initiatorOfferID;
    private final UUID proposedOfferID;
    int editedCount;
    private TradeDetails tradeDetails;
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
                 String initiatorUserID, String proposedUserID,
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
    public Trade(String initiatorUserID, String proposedUserID,
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
    public String getEditTurnUser() {
        return (editedCount % 2 == 0) ? proposedUserID : initiatorUserID;
    }

    /**
     * Checks if the given participant belongs to the trade.
     *
     * @param userID user ID
     * @return true/false
     */
    public boolean hasParticipant(String userID) {
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

    public String getInitiatorUserID() {
        return initiatorUserID;
    }

    public String getProposedUserID() {
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

    public void setTradeDetails(TradeDetails tradeDetails) {
        this.tradeDetails = tradeDetails;
    }

    public void updateTradeDetails(String place, DayOfWeek day, LocalTime time) {
        tradeDetails.update(place, day, time);
        lastUpdate = LocalDateTime.now();
    }
}
