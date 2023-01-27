package com.mechamic38.barattus.persistence.trade;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mechamic38.barattus.persistence.common.LocalDataSource;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

public class LocalTradeDataSource extends LocalDataSource implements ITradeDataSource {

    private static final File DATA = new File(
            System.getProperty("user.home") + File.separator + "Documents" + File.separator + "Barattus",
            "trade-data.json"
    );
    private static final String KEY = "trades";


    public LocalTradeDataSource() {
        super();
        createLocalFile(DATA);
    }

    @Override
    public List<TradeDTO> getAll() {
        JsonObject json = load(DATA, KEY);
        JsonArray tradeJson = json.getAsJsonArray(KEY);

        //System.out.println("Trades during populate:");
        //System.out.println(tradeJson);

        Type tradeType = new TypeToken<List<TradeDTO>>() {
        }.getType();
        List<TradeDTO> trades = gson.fromJson(
                tradeJson,
                tradeType
        );

        return trades;
    }

    @Override
    public boolean insert(TradeDTO tradeDTO) {
        JsonObject json = load(DATA, KEY);
        JsonArray trades = json.getAsJsonArray(KEY);
        //System.out.println("Trades during save:");
        //System.out.println(trades);

        JsonElement jsonTrade = gson.toJsonTree(tradeDTO);
        trades.add(jsonTrade);

        return uploadToFile(json, DATA);
    }


    @Override
    public boolean update(TradeDTO tradeDTO) {
        JsonObject json = load(DATA, KEY);
        JsonArray trades = json.getAsJsonArray(KEY);
        //System.out.println("Trades during save:");
        //System.out.println(trades);

        JsonElement oldJsonTrade = getTradeFromJson(
                trades,
                tradeDTO.getId()
        );
        JsonElement updatedJsonTrade = gson.toJsonTree(tradeDTO);

        trades.remove(oldJsonTrade);
        trades.add(updatedJsonTrade);

        return uploadToFile(json, DATA);
    }

    /**
     * Extracts the json Trade from the trade JsonArray, based on the given tradeID.
     *
     * @param trades  JsonArray of trades
     * @param tradeID Trade identifier
     * @return JsonElement trade that matches
     */
    private JsonElement getTradeFromJson(JsonArray trades, String tradeID) {
        for (JsonElement tradeJson : trades) {
            TradeDTO trade = gson.fromJson(tradeJson, TradeDTO.class);
            if (trade.getId().equals(tradeID)) {
                return tradeJson;
            }
        }

        return null;
    }
}
