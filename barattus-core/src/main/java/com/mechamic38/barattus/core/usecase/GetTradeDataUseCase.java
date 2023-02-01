package com.mechamic38.barattus.core.usecase;

import com.mechamic38.barattus.core.offer.IOfferRepository;
import com.mechamic38.barattus.core.trade.Trade;

/**
 * Returns all trade data, including offers and their categories.
 */
public class GetTradeDataUseCase implements IGetTradeDataUseCase {

    private final IGetOfferDataUseCase getOfferDataUseCase;
    private final IOfferRepository offerRepository;

    public GetTradeDataUseCase(IGetOfferDataUseCase getOfferDataUseCase, IOfferRepository offerRepository) {
        this.getOfferDataUseCase = getOfferDataUseCase;
        this.offerRepository = offerRepository;
    }

    @Override
    public TradeData apply(Trade trade) {
        var initiatorOfferData = getOfferDataUseCase.apply(
                offerRepository.getById(trade.getInitiatorOfferID())
        );
        var proposedOfferData = getOfferDataUseCase.apply(
                offerRepository.getById(trade.getProposedOfferID())
        );

        return TradeData.builder(trade)
                .setInitiatorOfferData(initiatorOfferData)
                .setProposedOfferData(proposedOfferData)
                .build();
    }
}
