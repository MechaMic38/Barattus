package com.mechamic38.barattus.gui.app;

import com.mechamic38.barattus.gui.login.LoginActivity;

public class BarattusApp extends BaseBarattusApplication {

    @Override
    public void init() throws Exception {
        launchGUI();
    }

    /**
     * Launches the main UI environment
     */
    private void launchGUI() {
        new LoginActivity().show();
    }
}
