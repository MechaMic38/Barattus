package com.mechamic38.barattus.core.trade;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class TradeRepositoryMock implements ITradeRepository {

    private final List<Trade> trades;

    public TradeRepositoryMock() {
        trades = new LinkedList<>();
    }

    @Override
    public Trade getById(UUID id) {
        return trades.stream()
                .filter(trade -> trade.checkID(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Trade> getAll() {
        return trades;
    }

    @Override
    public void save(Trade trade) {
        if (!this.trades.contains(trade)) {
            this.trades.add(trade);
        }
    }

    @Override
    public void loadFromDataSource() {

    }
}
