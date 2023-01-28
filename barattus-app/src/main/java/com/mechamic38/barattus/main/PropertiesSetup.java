package com.mechamic38.barattus.main;

public class PropertiesSetup {

    private static final String APP_NAME = "app.name";
    private static final String BARATTUS_VERSION = "app.version";

    private static final String APP_NAME_VALUE = "Barattus";
    private static final String BARATTUS_VERSION_VALUE = "1.0-SNAPSHOT";


    private PropertiesSetup() {

    }

    public static void setupSystemProperties() {
        putJFXProperties();
        putAppSpecificProperties();
    }

    private static void putJFXProperties() {
        com.sun.javafx.runtime.VersionInfo.setupSystemProperties();
    }

    private static void putAppSpecificProperties() {
        System.setProperty(APP_NAME, APP_NAME_VALUE);
        System.setProperty(BARATTUS_VERSION, BARATTUS_VERSION_VALUE);
    }
}
