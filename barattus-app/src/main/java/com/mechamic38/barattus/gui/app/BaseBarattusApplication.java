package com.mechamic38.barattus.gui.app;

import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.stage.Stage;

abstract public class BaseBarattusApplication extends Application {

    @Override
    final public void start(Stage primaryStage) throws Exception {

    }

    @Override
    abstract public void init() throws Exception;

    public static <T extends Class<? extends BaseBarattusApplication>> void launchApp(T appClass, String... args) {
        LauncherImpl.launchApplication(appClass, args);
    }
}
