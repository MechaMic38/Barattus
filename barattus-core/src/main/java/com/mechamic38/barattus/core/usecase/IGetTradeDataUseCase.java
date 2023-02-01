package com.mechamic38.barattus.core.usecase;

import com.mechamic38.barattus.core.trade.Trade;

import java.util.function.Function;

public interface IGetTradeDataUseCase extends Function<Trade, TradeData> {
}
