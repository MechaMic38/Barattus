package com.mechamic38.barattus.persistence.tradeparams;

import com.google.gson.*;
import com.mechamic38.barattus.persistence.common.InvalidFileException;
import com.mechamic38.barattus.persistence.common.LocalDataSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

public class LocalTradeParamDataSource extends LocalDataSource implements ITradeParamDataSource {

    private static final File DATA = new File(
            System.getProperty("barattus.dir.default.path"),
            "trade-params-data.json"
    );
    private static final String KEY = "tradeParams";


    public LocalTradeParamDataSource() {
        super();
        createLocalFile(DATA);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TradeParamDTO get() {
        JsonObject json;
        TradeParamDTO params;

        try {
            json = load(DATA, KEY);
            System.out.println(json);
            params = extractFromJson(json);
        } catch (InvalidFileException e) {
            params = getDefaultTradeParams();
        }

        return params;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TradeParamDTO get(String filePath) throws InvalidFileException {
        JsonObject json = loadFromExternalFile(filePath);
        TradeParamDTO params = extractFromJson(json);

        uploadToFile(json, DATA);
        return params;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insert(TradeParamDTO tradeParamsDTO) {
        JsonObject json = load(DATA, KEY);
        //System.out.println("Trade params during save:");
        //System.out.println(params);

        JsonElement jsonParams = gson.toJsonTree(tradeParamsDTO);
        json.remove(KEY);
        json.add(KEY, jsonParams);

        return uploadToFile(json, DATA);
    }

    @Override
    public boolean update(TradeParamDTO tradeParamsDTO) {
        return insert(tradeParamsDTO);

    }

    @Override
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
        return new JsonObject();
    }

    /**
     * Extracts the trade params from the JsonObject into a TradeParamsDTO.
     *
     * @param json JsonObject containing the trade params
     * @return TradeParamsDTO
     * @throws InvalidFileException if the selected file does not have valid format
     */
    private TradeParamDTO extractFromJson(JsonObject json) throws InvalidFileException {
        JsonObject paramsJson = json.getAsJsonObject(KEY);
        if (paramsJson == null) throw new InvalidFileException();

        try {
            return gson.fromJson(
                    paramsJson,
                    TradeParamDTO.class
            );
        } catch (JsonSyntaxException e) {
            throw new InvalidFileException();
        }
    }

    private TradeParamDTO getDefaultTradeParams() {
        return new TradeParamDTO(
                "",
                new LinkedList<>(),
                new LinkedList<>(),
                new LinkedList<>(),
                30
        );
    }
}
