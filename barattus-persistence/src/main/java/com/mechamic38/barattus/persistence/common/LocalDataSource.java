package com.mechamic38.barattus.persistence.common;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

abstract public class LocalDataSource {

    protected final Gson gson;

    protected LocalDataSource() {
        this.gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    }


    /**
     * Creates a local file, if it does not already exist.
     *
     * @param file File to create
     */
    public void createLocalFile(File file) {
        try {
            Path filePath = Paths.get(file.toURI());
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load the Json object related to the specified key from the file.
     *
     * @param file     File to read from
     * @param property Json property key
     * @return Json object
     */
    public JsonObject load(File file, String property) {
        try (
                BufferedReader reader = Files.newBufferedReader(Paths.get(file.toURI()), StandardCharsets.UTF_8)
        ) {

            JsonObject object;

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            object = gson.fromJson(
                    reader,
                    JsonObject.class
            );

            if (object != null) {
                return object;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.printf("Somehow got past here (%s)...\n", property);
        JsonObject json = new JsonObject();
        json.add(property, new JsonArray());
        return json;
    }

    /**
     * Uploads the given JsonObject to the selected file.
     *
     * @param json Json object
     * @param file File to write to
     * @return success/failure
     */
    public boolean uploadToFile(JsonObject json, File file) {
        try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(file.toURI()))
        ) {

            gson.toJson(
                    json,
                    writer
            );

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Loads a Json object from a file external to the application.
     *
     * @param filePath Path of the file to read from
     * @return Json object
     * @throws InvalidFileException If file does not exist or is invalid
     */
    public JsonObject loadFromExternalFile(String filePath) throws InvalidFileException {
        try (
                BufferedReader reader = Files.newBufferedReader(Paths.get(filePath), StandardCharsets.UTF_8)
        ) {

            JsonObject object;
            object = gson.fromJson(
                    reader,
                    JsonObject.class
            );

            if (object != null) {
                return object;
            } else {
                throw new InvalidFileException();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JsonSyntaxException | JsonIOException e) {
            throw new InvalidFileException();
        }
    }
}
