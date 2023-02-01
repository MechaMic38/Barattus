package com.mechamic38.barattus.core.usecase;

import com.mechamic38.barattus.core.trade.Trade;
import com.mechamic38.barattus.core.trade.TradeStatus;

import java.util.List;
import java.util.function.Function;

public interface IGetTradesByStatusUseCase extends Function<TradeStatus, List<Trade>> {

}
