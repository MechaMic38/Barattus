package com.mechamic38.barattus.core.trade;

/**
 * Enum for the different {@link Trade} statuses.
 */
public enum TradeStatus {
    ACCEPTED("trade.status.accepted"),
    EXPIRED("trade.status.expired"),
    ONGOING("trade.status.ongoing"),
    REJECTED("trade.status.rejected"),
    UNCONFIRMED("trade.status.unconfirmed");

    public final String i18n;


    TradeStatus(String i18n) {
        this.i18n = i18n;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
