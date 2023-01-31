package com.mechamic38.barattus.gui.trade;

import com.mechamic38.barattus.core.trade.ITradeService;

public class TradeListViewModel implements ITradeListViewModel {

    private final ITradeService tradeService;

    public TradeListViewModel(ITradeService tradeService) {
        this.tradeService = tradeService;
    }

    @Override
    public void initialize() {

    }
}
