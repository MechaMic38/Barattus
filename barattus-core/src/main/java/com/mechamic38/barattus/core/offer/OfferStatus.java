package com.mechamic38.barattus.core.offer;

/**
 * Enum for the different {@link Offer} statuses.
 */
public enum OfferStatus {
    OPEN("offer.status.open"),
    WITHDRAWN("offer.status.withdrawn"),
    COUPLED("offer.status.coupled"),
    SELECTED("offer.status.selected"),
    IN_EXCHANGE("offer.status.exchange"),
    CLOSED("offer.status.closed");

    private final String i18n;


    OfferStatus(String i18n) {
        this.i18n = i18n;
    }

    @Override
    public String toString() {
        //TODO: I18N Reference
        return super.toString();
    }
}
