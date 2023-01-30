package com.mechamic38.barattus.gui.common;

public enum Views {
    CATEGORY_EDITOR("CategoryEditor", "Barattus | Category Manager"),
    CATEGORY_LIST("CategoryList", "Barattus | Category Editor"),
    CREATE_OFFER("CreateOffer", "Barattus | Create Offer"),
    FIELD_EDITOR("FieldEditor", "Barattus | Field Editor"),
    MAIN("Main", "Barattus | Version 1.0"),
    LOGIN("Login", "Barattus | Barattus Login"),
    OFFER_DETAILS("OfferDetails", "Barattus | Offer Details"),
    OFFER_LIST("OfferList", "Barattus | Offer List"),
    REGISTER("Registration", "Barattus | Barattus Registration"),
    SELECT_TRADE_OFFER("SelectTradeOffer", "Barattus | Select Trade Offer"),
    TRADE_EDITOR("TradeEditor", "Barattus | Trade Editor"),
    TRADE_LIST("TradeList", "Barattus | Trade List"),
    TRADE_PARAMS("TradeParams", "Barattus | Trade Parameters Manager");

    public final String fxml;
    public final String title;

    Views(String fxml, String title) {
        this.fxml = fxml;
        this.title = title;
    }
}
