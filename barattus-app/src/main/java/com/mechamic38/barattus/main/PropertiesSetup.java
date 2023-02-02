package com.mechamic38.barattus.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PropertiesSetup {

    private static final String APP_NAME = "app.name";
    private static final String BARATTUS_VERSION = "app.version";
    private static final String BARATTUS_FOLDER = "barattus.dir.default.path";

    private static final String APP_NAME_VALUE = "Barattus";
    private static final String BARATTUS_VERSION_VALUE = "1.0.0";
    private static final String BARATTUS_DOCUMENTS_FOLDER = "BarattusDocuments";

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
        System.setProperty(BARATTUS_FOLDER, getDefaultDirectoryFilePath());
    }

    private static String getDefaultDirectoryFilePath() {
        File defaultDirectory = new File(
                System.getProperty("user.home"),
                BARATTUS_DOCUMENTS_FOLDER
        );
        createLocalDirectory(defaultDirectory);
        return defaultDirectory.getAbsolutePath();
    }

    private static void createLocalDirectory(File directory) {
        try {
            Path directoryPath = Paths.get(directory.toURI());
            if (!Files.exists(directoryPath)) {
                Files.createDirectory(directoryPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
