package com.mechamic38.barattus.gui.trade;

import com.mechamic38.barattus.core.offer.IOfferRepository;
import com.mechamic38.barattus.core.offer.IOfferService;
import com.mechamic38.barattus.core.trade.ITradeService;
import com.mechamic38.barattus.core.tradeparams.ITradeParamRepository;
import com.mechamic38.barattus.gui.common.SessionState;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TradeEditorViewModel implements ITradeEditorViewModel {

    private final ITradeService tradeService;
    private final IOfferService offerService;
    private final IOfferRepository offerRepository;
    private final ITradeParamRepository tradeParamRepository;

    private final StringProperty error = new SimpleStringProperty("");
    private final BooleanProperty editTurn = new SimpleBooleanProperty(false);
    private final BooleanProperty unconfirmed = new SimpleBooleanProperty(false);
    private final BooleanProperty ongoing = new SimpleBooleanProperty(false);

    public TradeEditorViewModel(ITradeService tradeService,
                                IOfferService offerService,
                                IOfferRepository offerRepository,
                                ITradeParamRepository tradeParamRepository) {
        this.tradeService = tradeService;
        this.offerService = offerService;
        this.offerRepository = offerRepository;
        this.tradeParamRepository = tradeParamRepository;
    }

    public boolean confirmTrade(String place, String day, String time) {
        return false;
    }

    public boolean editTrade(String place, String day, String time) {
        return false;
    }

    public boolean rejectTrade() {
        return false;
    }

    public boolean acceptTrade() {
        return false;
    }

    public void setInitiatorActive() {
        SessionState.getInstance().setOffer(
                SessionState.getInstance().getInitiatorOffer()
        );
    }

    public void setProposedActive() {
        SessionState.getInstance().setOffer(
                SessionState.getInstance().getProposedOffer()
        );
    }

    @Override
    public void initialize() {
        //TODO
    }

    @Override
    public StringProperty errorProperty() {
        return error;
    }

    @Override
    public BooleanProperty editTurnProperty() {
        return editTurn;
    }

    @Override
    public BooleanProperty unconfirmedProperty() {
        return unconfirmed;
    }

    @Override
    public BooleanProperty ongoingProperty() {
        return ongoing;
    }
}
