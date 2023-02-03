package com.mechamic38.barattus.gui.trade;

import com.mechamic38.barattus.core.offer.Offer;
import com.mechamic38.barattus.core.trade.ITradeService;
import com.mechamic38.barattus.core.trade.Trade;
import com.mechamic38.barattus.core.usecase.IGetCompatibleOffersUseCase;
import com.mechamic38.barattus.core.usecase.IGetOfferDataUseCase;
import com.mechamic38.barattus.core.usecase.OfferData;
import com.mechamic38.barattus.gui.common.SessionState;
import com.mechamic38.barattus.util.Result;
import javafx.beans.property.*;
import javafx.collections.FXCollections;

public class SelectTradeOfferViewModel implements ISelectTradeOfferViewModel {

    private final ITradeService tradeService;
    private final IGetCompatibleOffersUseCase getCompatibleOffersUseCase;
    private final IGetOfferDataUseCase getOfferDataUseCase;

    private final StringProperty error = new SimpleStringProperty("");
    private final ObjectProperty<OfferData> otherOffer = new SimpleObjectProperty<>();
    private final ListProperty<Offer> offers = new SimpleListProperty<>();

    public SelectTradeOfferViewModel(ITradeService tradeService,
                                     IGetCompatibleOffersUseCase getCompatibleOffersUseCase,
                                     IGetOfferDataUseCase getOfferDataUseCase) {
        this.tradeService = tradeService;
        this.getCompatibleOffersUseCase = getCompatibleOffersUseCase;
        this.getOfferDataUseCase = getOfferDataUseCase;
    }

    @Override
    public void initialize() {
        otherOffer.set(getOfferDataUseCase.apply(
                SessionState.getInstance().getOffer()
        ));

        offers.set(FXCollections.observableList(
                getCompatibleOffersUseCase.apply(otherOffer.get().getOffer()).stream()
                        .filter(offer -> SessionState.getInstance().getUser().checkID(offer.getUserID()))
                        .toList()
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
    public ObjectProperty<OfferData> otherOfferProperty() {
        return otherOffer;
    }

    @Override
    public ListProperty<Offer> offersProperty() {
        return offers;
    }
}
