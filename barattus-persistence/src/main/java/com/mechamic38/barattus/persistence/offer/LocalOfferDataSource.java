package com.mechamic38.barattus.persistence.offer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mechamic38.barattus.persistence.common.LocalDataSource;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

public class LocalOfferDataSource extends LocalDataSource implements IOfferDataSource {

    private static final File DATA = new File(
            System.getProperty("user.home") + File.separator + "Documents" + File.separator + "Barattus",
            "offers-data.json"
    );
    private static final String KEY = "offers";


    public LocalOfferDataSource() {
        super();
        createLocalFile(DATA);
    }

    @Override
    public List<OfferDTO> getAll() {
        JsonObject json = load(DATA, KEY);
        JsonArray offersJson = json.getAsJsonArray(KEY);

        //System.out.println("Trade offers during populate:");
        //System.out.println(offersJson);

        Type offersType = new TypeToken<List<OfferDTO>>() {
        }.getType();
        List<OfferDTO> offers = gson.fromJson(
                offersJson,
                offersType
        );

        return offers;
    }

    @Override
    public boolean insert(OfferDTO offerDTO) {
        JsonObject json = load(DATA, KEY);
        JsonArray offers = json.getAsJsonArray(KEY);
        //System.out.println("Trade offers during save:");
        //System.out.println(offers);

        JsonElement jsonOffer = gson.toJsonTree(offerDTO);
        offers.add(jsonOffer);

        return uploadToFile(json, DATA);
    }


    @Override
    public boolean update(OfferDTO offerDTO) {
        JsonObject json = load(DATA, KEY);
        JsonArray offers = json.getAsJsonArray(KEY);
        //System.out.println("Trade offers during save:");
        //System.out.println(offers);

        JsonElement oldJsonOffer = getTradeOfferFromJson(
                offers,
                offerDTO.getId()
        );
        JsonElement updatedJsonOffer = gson.toJsonTree(offerDTO);

        offers.remove(oldJsonOffer);
        offers.add(updatedJsonOffer);

        return uploadToFile(json, DATA);
    }

    /**
     * Extracts the json TradeOffer from the trade offer JsonArray, based on the given offerID.
     *
     * @param offers  JsonArray of trade offers
     * @param offerID Creation date of the offer
     * @return JsonElement trade offer that matches
     */
    private JsonElement getTradeOfferFromJson(JsonArray offers, String offerID) {
        for (JsonElement offerJson : offers) {
            OfferDTO offer = gson.fromJson(offerJson, OfferDTO.class);
            if (offer.getId().equals(offerID)) {
                return offerJson;
            }
        }

        return null;
    }
}
