package com.mechamic38.barattus.gui.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Builder class for {@link Alert}.
 */
public final class DialogBuilder {
    private final Alert.AlertType type;
    private final ButtonType[] buttonTypes;
    private final String title;
    private final String content;

    public DialogBuilder(String title, String content, Alert.AlertType type, ButtonType... buttonTypes) {
        this.title = title;
        this.content = content;
        this.buttonTypes = buttonTypes;
        this.type = type;
    }

    public final Alert build() {
        Alert alert = new Alert(type, content, buttonTypes);
        alert.setTitle(title);
        return alert;
    }
}
