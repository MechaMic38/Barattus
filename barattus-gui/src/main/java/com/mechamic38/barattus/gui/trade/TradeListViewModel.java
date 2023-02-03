package com.mechamic38.barattus.gui.trade;

import com.mechamic38.barattus.core.trade.Trade;
import com.mechamic38.barattus.core.trade.TradeStatus;
import com.mechamic38.barattus.core.usecase.IGetTradeDataUseCase;
import com.mechamic38.barattus.core.usecase.IQueryTradesUseCase;
import com.mechamic38.barattus.core.usecase.TradeData;
import com.mechamic38.barattus.core.usecase.TradeQuery;
import com.mechamic38.barattus.gui.common.SessionState;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import java.util.List;

public class TradeListViewModel implements ITradeListViewModel {

    private final IQueryTradesUseCase queryTradesUseCase;
    private final IGetTradeDataUseCase getTradeDataUseCase;

    private final ListProperty<TradeData> trades = new SimpleListProperty<>();

    public TradeListViewModel(IQueryTradesUseCase queryTradesUseCase,
                              IGetTradeDataUseCase getTradeDataUseCase) {
        this.queryTradesUseCase = queryTradesUseCase;
        this.getTradeDataUseCase = getTradeDataUseCase;
    }

    @Override
    public void setActiveTrade(Trade trade) {
        SessionState.getInstance().setTrade(trade);
    }

    @Override
    public void loadTrades(TradeStatus status) {
        TradeQuery query = TradeQuery.builder()
                .setUser(SessionState.getInstance().getUser())
                .addStatus(status)
                .build();
        List<Trade> userTrades = queryTradesUseCase.apply(query);

        List<TradeData> tradesData = userTrades.stream()
                .map(getTradeDataUseCase)
                .toList();

        trades.set(FXCollections.observableList(tradesData));
    }

    @Override
    public void initialize() {

    }

    @Override
    public ListProperty<TradeData> tradesProperty() {
        return trades;
    }
}
