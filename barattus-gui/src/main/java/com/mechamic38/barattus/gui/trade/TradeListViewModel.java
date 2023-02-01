package com.mechamic38.barattus.gui.trade;

import com.mechamic38.barattus.core.trade.ITradeService;
import com.mechamic38.barattus.core.trade.Trade;
import com.mechamic38.barattus.core.trade.TradeStatus;
import com.mechamic38.barattus.gui.common.SessionState;

public class TradeListViewModel implements ITradeListViewModel {

    private final ITradeService tradeService;

    public TradeListViewModel(ITradeService tradeService) {
        this.tradeService = tradeService;
    }

    @Override
    public void setActiveTrade(Trade trade) {
        SessionState.getInstance().setTrade(trade);
    }

    @Override
    public void loadTrades(TradeStatus status) {
        //TODO
    }

    @Override
    public void initialize() {

    }
}
