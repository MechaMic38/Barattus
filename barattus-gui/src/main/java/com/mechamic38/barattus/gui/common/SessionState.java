package com.mechamic38.barattus.gui.common;

import com.mechamic38.barattus.core.category.Category;
import com.mechamic38.barattus.core.category.CategoryField;
import com.mechamic38.barattus.core.offer.Offer;
import com.mechamic38.barattus.core.trade.Trade;
import com.mechamic38.barattus.core.user.User;

public class SessionState {

    private static SessionState INSTANCE;

    private boolean adminMode;

    private User user;
    private Category category;
    private CategoryField field;

    private Offer offer;

    private Offer initiatorOffer;
    private Offer proposedOffer;
    private Trade trade;

    private SessionState() {
    }

    public static SessionState getInstance() {
        if (INSTANCE == null) {
            synchronized (SessionState.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SessionState();
                }
            }
        }
        return INSTANCE;
    }

    public void reset() {
        adminMode = false;
        user = null;
        category = null;
        field = null;
        offer = null;
        initiatorOffer = null;
        proposedOffer = null;
        trade = null;
    }

    public boolean isAdminMode() {
        return adminMode;
    }

    public void setAdminMode(boolean adminMode) {
        this.adminMode = adminMode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public CategoryField getField() {
        return field;
    }

    public void setField(CategoryField field) {
        this.field = field;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Offer getInitiatorOffer() {
        return initiatorOffer;
    }

    public void setInitiatorOffer(Offer initiatorOffer) {
        this.initiatorOffer = initiatorOffer;
    }

    public Offer getProposedOffer() {
        return proposedOffer;
    }

    public void setProposedOffer(Offer proposedOffer) {
        this.proposedOffer = proposedOffer;
    }

    public Trade getTrade() {
        return trade;
    }

    public void setTrade(Trade trade) {
        this.trade = trade;
    }
}
