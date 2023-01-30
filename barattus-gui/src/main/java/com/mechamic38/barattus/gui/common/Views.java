package com.mechamic38.barattus.gui.common;

public enum Views {
    LOGIN("Login", "Barattus Login"),
    REGISTER("Registration", "Barattus Registration");

    public final String fxml;
    public final String title;

    Views(String fxml, String title) {
        this.fxml = fxml;
        this.title = title;
    }
}
