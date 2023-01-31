package com.mechamic38.barattus.gui.trade;

import com.mechamic38.barattus.core.offer.IOfferRepository;
import com.mechamic38.barattus.core.offer.IOfferService;
import com.mechamic38.barattus.core.trade.ITradeService;
import com.mechamic38.barattus.core.tradeparams.ITradeParamRepository;

public class TradeEditorViewModel implements ITradeEditorViewModel {

    private final ITradeService tradeService;
    private final IOfferService offerService;
    private final IOfferRepository offerRepository;
    private final ITradeParamRepository tradeParamRepository;

    public TradeEditorViewModel(ITradeService tradeService,
                                IOfferService offerService,
                                IOfferRepository offerRepository,
                                ITradeParamRepository tradeParamRepository) {
        this.tradeService = tradeService;
        this.offerService = offerService;
        this.offerRepository = offerRepository;
        this.tradeParamRepository = tradeParamRepository;
    }

    @Override
    public void initialize() {

    }
}
