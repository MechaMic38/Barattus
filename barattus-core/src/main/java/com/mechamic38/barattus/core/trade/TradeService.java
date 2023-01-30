package com.mechamic38.barattus.core.trade;

import com.mechamic38.barattus.core.offer.IOfferRepository;
import com.mechamic38.barattus.core.offer.IOfferService;
import com.mechamic38.barattus.core.tradeparams.ITradeParamRepository;

public class TradeService implements ITradeService {

    private final ITradeRepository tradeRepository;
    private final IOfferService offerService;
    private final IOfferRepository offerRepository;
    private final ITradeParamRepository tradeParamRepository;

    public TradeService(ITradeRepository tradeRepository, IOfferService offerService,
                        IOfferRepository offerRepository, ITradeParamRepository tradeParamRepository) {
        this.tradeRepository = tradeRepository;
        this.offerService = offerService;
        this.offerRepository = offerRepository;
        this.tradeParamRepository = tradeParamRepository;
    }
}
