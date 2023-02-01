package com.mechamic38.barattus.core.trade;

import com.mechamic38.barattus.core.offer.Offer;
import com.mechamic38.barattus.core.user.User;
import com.mechamic38.barattus.util.Result;

public interface ITradeService {
    Result<Trade> createTrade(Offer initiatorOffer, Offer proposedOffer);

    Result<Trade> rejectTrade(Trade trade, User user);

    Result<Trade> confirmTrade(Trade trade, TradeDetails details, User user);

    Result<Trade> acceptTrade(Trade trade, User user);

    Result<Trade> editTradeDetails(Trade trade, TradeDetails details, User user);
}
