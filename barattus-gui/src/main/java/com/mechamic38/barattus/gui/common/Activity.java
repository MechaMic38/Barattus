package com.mechamic38.barattus.gui.common;

import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.NonBlocking;

import java.util.function.Consumer;

public interface Activity extends Context {

    void show();

    View getView();

    void setView(View view);

    /**
     * Shows an error alert dialog.
     *
     * @param title    the title of the dialog
     * @param message  the message of the dialog
     * @param onResult the action when the user clicks the OK button
     */
    @NonBlocking
    ContextDialog showErrorDialog(
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
    ContextDialog showErrorDialog(
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
     * Shows an information dialog.
     *
     * @param title    the title
     * @param message  the message
     * @param onResult the action that handles the button click-s on the dialog
     */
    @NonBlocking
    ContextDialog showInformationDialog(
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
    ContextDialog showConfirmationDialog(
            String title,
            String message,
            Consumer<ButtonType> onResult
    );

    @NonBlocking
    ContextDialog showDialog(
            String title,
            Node content,
            Consumer<ButtonType> onResult,
            ButtonType... buttonTypes
    );

    @Blocking
    ButtonType showErrorDialogAndWait(
            String title,
            String message
    );

    @Blocking
    ButtonType showErrorDialogAndWait(
            String title,
            String message,
            Exception e
    );

    @Blocking
    ButtonType showInformationDialogAndWait(
            String title,
            String message
    );

    @Blocking
    ButtonType showConfirmationDialogAndWait(
            String title,
            String message
    );

    @Blocking
    ButtonType showDialogAndWait(
            String title,
            Node content,
            ButtonType... buttonTypes
    );
}
