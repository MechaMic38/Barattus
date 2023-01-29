package com.mechamic38.barattus.gui.app;

import com.mechamic38.barattus.gui.login.LoginActivity;
import javafx.application.Platform;

public class BarattusApp extends BaseBarattusApplication {

    @Override
    public void init() {
        launchGUI();
    }

    /**
     * Launches the main UI environment
     */
    private void launchGUI() {
        Platform.runLater(() -> new LoginActivity().show());
    }
}
