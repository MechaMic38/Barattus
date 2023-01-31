package com.mechamic38.barattus.core.trade;

import com.mechamic38.barattus.core.offer.Offer;
import com.mechamic38.barattus.util.Result;

public interface ITradeService {
    Result<Trade> createTrade(Offer initiatorOffer, Offer proposedOffer);
}
