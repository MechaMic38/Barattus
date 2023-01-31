package com.mechamic38.barattus.gui.trade;

import com.mechamic38.barattus.core.offer.IOfferRepository;
import com.mechamic38.barattus.core.offer.IOfferService;
import com.mechamic38.barattus.core.offer.Offer;
import com.mechamic38.barattus.core.trade.ITradeService;
import com.mechamic38.barattus.core.trade.Trade;
import com.mechamic38.barattus.gui.common.SessionState;
import com.mechamic38.barattus.util.Result;
import javafx.beans.property.*;
import javafx.collections.FXCollections;

public class SelectTradeOfferViewModel implements ISelectTradeOfferViewModel {

    private final ITradeService tradeService;
    private final IOfferService offerService;
    private final IOfferRepository offerRepository;

    private final StringProperty error = new SimpleStringProperty("");
    private final ObjectProperty<Offer> otherOffer = new SimpleObjectProperty<>();
    private final ListProperty<Offer> offers = new SimpleListProperty<>();

    public SelectTradeOfferViewModel(ITradeService tradeService,
                                     IOfferService offerService,
                                     IOfferRepository offerRepository) {
        this.tradeService = tradeService;
        this.offerService = offerService;
        this.offerRepository = offerRepository;
    }

    @Override
    public void initialize() {
        otherOffer.set(
                SessionState.getInstance().getOffer()
        );
        offers.set(FXCollections.observableList(
                offerService.getCompatibleOffers(
                        otherOffer.get(),
                        SessionState.getInstance().getUser()
                )
        ));
    }

    @Override
    public boolean proposeTrade(Offer ownOffer) {
        Result<Trade> result = tradeService.createTrade(
                ownOffer,
                SessionState.getInstance().getOffer()
        );

        if (result.isError()) {
            error.set(result.getError());
            return false;
        } else {
            SessionState.getInstance().setTrade(result.getResult());
            return true;
        }
    }

    @Override
    public StringProperty errorProperty() {
        return error;
    }

    @Override
    public ObjectProperty<Offer> otherOfferProperty() {
        return otherOffer;
    }

    @Override
    public ListProperty<Offer> offersProperty() {
        return offers;
    }
}
