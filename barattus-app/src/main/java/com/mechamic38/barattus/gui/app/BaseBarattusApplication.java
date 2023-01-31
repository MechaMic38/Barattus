package com.mechamic38.barattus.gui.app;

import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.stage.Stage;

abstract public class BaseBarattusApplication extends Application {

    public static <T extends Class<? extends BaseBarattusApplication>> void launchApp(T appClass, String... args) {
        LauncherImpl.launchApplication(appClass, args);
    }

    @Override
    final public void start(Stage primaryStage) {

    }

    /**
     * Should initialize and launch the javafx app
     */
    @Override
    abstract public void init();
}
