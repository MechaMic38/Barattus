package com.mechamic38.barattus.core.tradeparams;

import com.mechamic38.barattus.persistence.tradeparams.ITradeParamDataSource;
import com.mechamic38.barattus.persistence.tradeparams.TradeParamDTO;

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
        return tradeParams.clone();
    }

    @Override
    public void save(TradeParams tradeParams) {
        this.tradeParams = tradeParams;
        dataSource.update(mapper.toDto(tradeParams));
    }

    @Override
    public void loadFromDataSource() {
        TradeParamDTO dto = dataSource.get();
        if (dto != null) {
            tradeParams = mapper.fromDto(dto);
        } else {
            tradeParams = TradeParams.getDefaultParams();
        }
    }

    @Override
    public void importData(TradeParams tradeParams) {
        this.tradeParams = tradeParams;
    }
}
