package com.mechamic38.barattus.gui.common;

import javafx.scene.control.ButtonType;
import org.jetbrains.annotations.NonBlocking;

import java.util.function.Consumer;

public interface Activity extends Context {

    void show();

    View getView();

    void setView(View view);

    void setView(Views view);

    /**
     * Shows an error alert dialog.
     *
     * @param title    the title of the dialog
     * @param message  the message of the dialog
     * @param onResult the action when the user clicks the OK button
     */
    @NonBlocking
    void showErrorDialog(
            String title,
            String message,
            Consumer<ButtonType> onResult
    );

    /**
     * Shows an error alert dialog. Also, it can show
     * the stacktrace of an exception.
     *
     * @param title    the title of the dialog
     * @param message  the message of the dialog
     * @param cause    the Exception that caused the dialog
     * @param onResult the action when the user clicks the OK button
     */
    @NonBlocking
    void showErrorDialog(
            String title,
            String message,
            Exception cause,
            Consumer<ButtonType> onResult
    );

    @NonBlocking
    default void showErrorDialog(String title, String message) {
        this.showErrorDialog(title, message, button -> {
        });
    }

    @NonBlocking
    default void showErrorDialog(String title, String message, Exception cause) {
        this.showErrorDialog(title, message, cause, button -> {
        });
    }

    /**
     * Shows a warning dialog.
     *
     * @param title    the title
     * @param message  the message
     * @param onResult the action that handles the button click-s on the dialog
     */
    @NonBlocking
    void showWarningDialog(
            String title,
            String message,
            Consumer<ButtonType> onResult
    );

    /**
     * Shows an information dialog.
     *
     * @param title    the title
     * @param message  the message
     * @param onResult the action that handles the button click-s on the dialog
     */
    @NonBlocking
    void showInformationDialog(
            String title,
            String message,
            Consumer<ButtonType> onResult
    );

    /**
     * Shows a confirmation dialog.
     *
     * @param title    the title
     * @param message  the message
     * @param onResult the action that handles the button click-s on the dialog
     */
    @NonBlocking
    void showConfirmationDialog(
            String title,
            String message,
            Consumer<ButtonType> onResult
    );
}
