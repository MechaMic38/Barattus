package com.mechamic38.barattus.core.usecase;

import com.mechamic38.barattus.core.trade.Trade;

import java.util.List;
import java.util.function.Function;

public interface IQueryTradesUseCase extends Function<TradeQuery, List<Trade>> {
}
