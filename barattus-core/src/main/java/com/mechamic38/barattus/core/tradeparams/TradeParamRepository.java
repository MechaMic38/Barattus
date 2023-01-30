package com.mechamic38.barattus.core.tradeparams;

import com.mechamic38.barattus.persistence.tradeparams.ITradeParamDataSource;

/**
 * Trade parameters repository.
 */
public class TradeParamRepository implements ITradeParamRepository {

    private final ITradeParamDataSource dataSource;
    private final TradeParamsMapper mapper;
    private TradeParams tradeParams;


    public TradeParamRepository(ITradeParamDataSource dataSource, TradeParamsMapper mapper) {
        this.dataSource = dataSource;
        this.mapper = mapper;
    }

    @Override
    public TradeParams get() {
        return tradeParams;
    }

    @Override
    public void save(TradeParams tradeParams) {
        this.tradeParams = tradeParams;
        dataSource.update(mapper.toDto(tradeParams));
    }
}
