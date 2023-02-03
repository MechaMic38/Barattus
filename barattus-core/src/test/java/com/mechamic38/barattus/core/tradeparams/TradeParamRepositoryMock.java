package com.mechamic38.barattus.core.tradeparams;

public class TradeParamRepositoryMock implements ITradeParamRepository {

    private TradeParams tradeParams;

    @Override
    public TradeParams get() {
        return tradeParams;
    }

    @Override
    public void save(TradeParams tradeParams) {
        this.tradeParams = tradeParams;
    }

    @Override
    public void loadFromDataSource() {

    }

    @Override
    public void importData(TradeParams tradeParams) {
        this.tradeParams = tradeParams;
    }
}
