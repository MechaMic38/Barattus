package com.mechamic38.barattus.gui.trade;

import com.mechamic38.barattus.core.trade.Trade;
import com.mechamic38.barattus.core.trade.TradeStatus;
import com.mechamic38.barattus.gui.common.ViewModel;

public interface ITradeListViewModel extends ViewModel {
    void setActiveTrade(Trade trade);

    void loadTrades(TradeStatus status);
}
