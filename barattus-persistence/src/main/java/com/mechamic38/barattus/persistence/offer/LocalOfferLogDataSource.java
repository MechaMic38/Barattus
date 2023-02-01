package com.mechamic38.barattus.persistence.offer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mechamic38.barattus.persistence.common.LocalDataSource;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

public class LocalOfferLogDataSource extends LocalDataSource implements IOfferLogDataSource {

    private static final File DATA = new File(
            System.getProperty("barattus.dir.default.path"),
            "offer-logs-data.json"
    );
    private static final String KEY = "offerLogs";


    public LocalOfferLogDataSource() {
        super();
        createLocalFile(DATA);
    }

    @Override
    public List<OfferLogDTO> getAll() {
        JsonObject json = load(DATA, KEY);
        JsonArray offerLogsJson = json.getAsJsonArray(KEY);

        //System.out.println("Trade logs during populate:");
        //System.out.println(offerLogsJson);

        Type offerLogsType = new TypeToken<List<OfferLogDTO>>() {
        }.getType();
        List<OfferLogDTO> offerLogs = gson.fromJson(
                offerLogsJson,
                offerLogsType
        );

        return offerLogs;
    }

    @Override
    public boolean insert(OfferLogDTO offerLogDTO) {
        JsonObject json = load(DATA, KEY);
        JsonArray offerLogs = json.getAsJsonArray(KEY);
        //System.out.println("Trade logs during save:");
        //System.out.println(offerLogs);

        JsonElement jsonOfferLog = gson.toJsonTree(offerLogDTO);
        offerLogs.add(jsonOfferLog);

        return uploadToFile(json, DATA);
    }

    @Override
    public boolean update(OfferLogDTO entity) {
        return false;
    }
}
