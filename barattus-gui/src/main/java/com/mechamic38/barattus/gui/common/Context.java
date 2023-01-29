package com.mechamic38.barattus.gui.common;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Window;
import org.jetbrains.annotations.NonBlocking;
import org.jetbrains.annotations.NotNull;

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

    Scene getContextScene();

    Window getContextWindow();

    void focusRequest();

    void makeFocused();

    void close();
}
