package com.mechamic38.barattus.core.offer;

import com.mechamic38.barattus.core.common.Entity;
import com.mechamic38.barattus.core.user.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Trade offer posted by a user.
 */
public class Offer extends Entity<UUID> {

    private final LocalDateTime creationDate;
    private final String categoryID;
    private final String userID;
    private final Set<OfferField> offerFields;
    private String title;
    private OfferStatus status;


    /**
     * Creates a new offer with the given unique identifier, userID, categoryID, title and creationDate.
     *
     * @param uuid         Unique identifier
     * @param userID       User identifier the offer belongs to
     * @param categoryID   Category identifier the offer belongs to
     * @param title        Title of the offer
     * @param creationDate Time the offer was created
     */
    public Offer(UUID uuid, String userID, String categoryID, String title, LocalDateTime creationDate) {
        this(userID, categoryID, title, creationDate);
        this.id = uuid;
    }

    /**
     * Creates a new offer with the given userID, categoryID, title and creationDate.
     *
     * @param userID       User identifier the offer belongs to
     * @param categoryID   Category identifier the offer belongs to
     * @param title        Title of the offer
     * @param creationDate Time the offer was created
     */
    public Offer(String userID, String categoryID, String title, LocalDateTime creationDate) {
        this.userID = userID;
        this.categoryID = categoryID;
        this.title = title;
        this.creationDate = creationDate;
        this.offerFields = new LinkedHashSet<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public String getUserID() {
        return userID;
    }

    public OfferStatus getStatus() {
        return status;
    }

    public void setStatus(OfferStatus status) {
        this.status = status;
    }

    public Set<OfferField> getOfferFields() {
        return offerFields;
    }

    public void addOfferFields(Collection<OfferField> fields) {
        offerFields.addAll(fields);
    }

    public void removeOfferFields(Collection<OfferField> fields) {
        offerFields.removeAll(fields);
    }

    protected boolean addOfferField(OfferField field) {
        return offerFields.add(field);
    }

    protected boolean removeOfferField(OfferField field) {
        return offerFields.add(field);
    }

    public boolean isOwner(User user) {
        return userID.equals(user.getUsername());
    }

    public boolean canWithdraw(User user) {
        return userID.equals(user.getID()) && status == OfferStatus.OPEN;
    }

    public boolean canPropose(User user) {
        return !userID.equals(user.getID()) && status == OfferStatus.OPEN;
    }
}
