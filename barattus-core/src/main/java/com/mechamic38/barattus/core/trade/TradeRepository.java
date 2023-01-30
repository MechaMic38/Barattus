package com.mechamic38.barattus.core.trade;

import com.mechamic38.barattus.persistence.trade.ITradeDataSource;
import com.mechamic38.barattus.persistence.trade.TradeDTO;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Trades repository.
 */
public class TradeRepository implements ITradeRepository {

    private final List<Trade> trades;
    private final ITradeDataSource dataSource;
    private final TradeMapper mapper;


    public TradeRepository(ITradeDataSource dataSource, TradeMapper mapper) {
        this.dataSource = dataSource;
        this.mapper = mapper;
        this.trades = new LinkedList<>();
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
            dataSource.insert(mapper.toDto(trade));
        } else {
            dataSource.update(mapper.toDto(trade));
        }
    }

    @Override
    public void loadFromDataSource() {
        for (TradeDTO trade : dataSource.getAll()) {
            trades.add(mapper.fromDto(trade));
        }
    }
}
