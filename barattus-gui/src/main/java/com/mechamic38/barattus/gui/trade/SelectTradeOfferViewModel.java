package com.mechamic38.barattus.gui.trade;

import com.mechamic38.barattus.core.offer.IOfferRepository;
import com.mechamic38.barattus.core.offer.IOfferService;
import com.mechamic38.barattus.core.trade.ITradeService;

public class SelectTradeOfferViewModel implements ISelectTradeOfferViewModel {

    private final ITradeService tradeService;
    private final IOfferService offerService;
    private final IOfferRepository offerRepository;

    public SelectTradeOfferViewModel(ITradeService tradeService,
                                     IOfferService offerService,
                                     IOfferRepository offerRepository) {
        this.tradeService = tradeService;
        this.offerService = offerService;
        this.offerRepository = offerRepository;
    }
}
