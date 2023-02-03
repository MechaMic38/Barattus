package com.mechamic38.barattus.core.usecase;

import com.mechamic38.barattus.core.trade.ITradeRepository;
import com.mechamic38.barattus.core.trade.Trade;

import java.util.List;

public class QueryTradesUseCase implements IQueryTradesUseCase {

    private final ITradeRepository tradeRepository;

    public QueryTradesUseCase(ITradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Override
    public List<Trade> apply(TradeQuery tradeQuery) {
        return tradeRepository.getAll().stream()
                .filter(trade -> {
                    if (tradeQuery.getUser() != null) {
                        return trade.hasParticipant(tradeQuery.getUser().getID());
                    } else {
                        return true;
                    }
                })
                .filter(trade -> {
                    if (tradeQuery.getOffer() != null) {
                        return trade.hasOffer(tradeQuery.getOffer().getID());
                    } else {
                        return true;
                    }
                })
                .filter(trade -> {
                    if (!tradeQuery.getStatuses().isEmpty()) {
                        return tradeQuery.getStatuses().contains(trade.getStatus());
                    } else {
                        return true;
                    }
                })
                .toList();
    }
}
