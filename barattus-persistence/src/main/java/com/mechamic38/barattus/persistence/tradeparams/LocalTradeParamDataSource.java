package com.mechamic38.barattus.persistence.tradeparams;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mechamic38.barattus.persistence.common.InvalidFileException;
import com.mechamic38.barattus.persistence.common.LocalDataSource;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class LocalTradeParamDataSource extends LocalDataSource implements ITradeParamDataSource {

    private static final File DATA = new File(
            System.getProperty("user.home") + File.separator + "Documents" + File.separator + "Barattus",
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
    public List<TradeParamDTO> getAll() {
        JsonObject json;
        TradeParamDTO params;

        try {
            json = load(DATA, KEY);
            params = extractFromJson(json);
        } catch (InvalidFileException e) {
            params = getDefaultTradeParams();
        }

        return List.of(params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TradeParamDTO> getAll(String filePath) throws InvalidFileException {
        JsonObject json = loadFromExternalFile(filePath);
        TradeParamDTO params = extractFromJson(json);

        uploadToFile(json, DATA);
        return List.of(params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insert(TradeParamDTO tradeParamsDTO) {
        JsonObject json = load(DATA, KEY);
        JsonObject params = json.getAsJsonObject("tradeParams");
        //System.out.println("Trade params during save:");
        //System.out.println(params);

        JsonElement jsonParams = gson.toJsonTree(tradeParamsDTO);
        json.remove("tradeParams");
        json.add("tradeParams", jsonParams);

        return uploadToFile(json, DATA);
    }

    @Override
    public boolean update(TradeParamDTO tradeParamsDTO) {
        return insert(tradeParamsDTO);
    }

    /**
     * Extracts the trade params from the JsonObject into a TradeParamsDTO.
     *
     * @param json JsonObject containing the trade params
     * @return TradeParamsDTO
     * @throws InvalidFileException
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
