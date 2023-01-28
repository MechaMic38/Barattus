package com.mechamic38.barattus.gui.api;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import javafx.stage.Window;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.NonBlocking;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * A Context is an abstract structure that allows to interact with a particular GUI environment.
 */
public interface Context {

    @NotNull ObservableList<Region> getBlockingOverlaysShown();

    @NotNull ObservableList<Region> getNonBlockingOverlaysShown();

    /**
     * Shows a popup (on the center) with the GUI-element defined.
     *
     * @param region   the {@link Region} GUI element to be shown
     * @param blocking {@code false} if clicking outside of the popup should close it.
     */
    @NonBlocking
    void showOverlay(Region region, boolean blocking);

    /**
     * @see [showOverlay]
     */
    @NonBlocking
    default void showOverlay(Region region) {
        showOverlay(region, false);
    }

    /**
     * Hides the popup (if it's showing).
     *
     * @param region to be hidden
     */
    void hideOverlay(Region region);


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
    ButtonType showErrorDialogAndWait(String title, String message);

    @Blocking
    ButtonType showErrorDialogAndWait(
            String title,
            String message,
            Exception e
    );

    @Blocking
    ButtonType showInformationDialogAndWait(String title, String message);

    @Blocking
    ButtonType showConfirmationDialogAndWait(String title, String message);

    @Blocking
    ButtonType showDialogAndWait(
            String title,
            Node content,
            ButtonType... buttonTypes
    );

    Scene getContextScene();

    Window getContextWindow();

    void focusRequest();

    void toFrontRequest();

    Boolean isShowing();

    void close();
}
