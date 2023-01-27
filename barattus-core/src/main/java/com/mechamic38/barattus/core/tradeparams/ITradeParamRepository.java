package com.mechamic38.barattus.core.tradeparams;

public interface ITradeParamRepository {

    TradeParams get();

    void save(TradeParams tradeParams);
}
