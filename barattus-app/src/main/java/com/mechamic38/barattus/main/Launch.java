package com.mechamic38.barattus.main;

import com.mechamic38.barattus.core.common.ServiceProvider;
import com.mechamic38.barattus.gui.app.BarattusApp;
import com.mechamic38.barattus.gui.app.BaseBarattusApplication;

public class Launch {

    public static void main(String[] args) {
        init();
        launch(args);
    }

    private static void init() {
        PropertiesSetup.setupSystemProperties();
        ServiceProvider.getInstance().buildServices();
    }

    private static void launch(String[] args) {
        BaseBarattusApplication.launchApp(BarattusApp.class, args);
    }
}
