package com.mechamic38.barattus.core.usecase;

import com.mechamic38.barattus.core.trade.ITradeRepository;
import com.mechamic38.barattus.core.trade.Trade;
import com.mechamic38.barattus.core.trade.TradeStatus;

import java.util.List;
import java.util.stream.Collectors;

public class GetTradeByStatusUseCase implements IGetTradesByStatusUseCase {

    private final ITradeRepository tradeRepository;

    public GetTradeByStatusUseCase(ITradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Override
    public List<Trade> apply(TradeStatus status) {
        return tradeRepository.getAll().stream()
                .filter(trade -> trade.getStatus() == status)
                .collect(Collectors.toList());
    }
}
