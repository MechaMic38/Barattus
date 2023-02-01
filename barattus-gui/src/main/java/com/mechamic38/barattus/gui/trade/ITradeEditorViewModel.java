package com.mechamic38.barattus.gui.trade;

import com.mechamic38.barattus.core.usecase.TradeData;
import com.mechamic38.barattus.gui.common.ViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import java.time.DayOfWeek;
import java.time.LocalTime;

public interface ITradeEditorViewModel extends ViewModel {

    boolean confirmTrade(String place, DayOfWeek day, LocalTime time);

    boolean editTrade(String place, DayOfWeek day, LocalTime time);

    boolean rejectTrade();

    boolean acceptTrade();

    void setInitiatorActive();

    void setProposedActive();

    StringProperty errorProperty();

    BooleanProperty editTurnProperty();

    BooleanProperty unconfirmedProperty();

    BooleanProperty ongoingProperty();

    ObjectProperty<TradeData> tradeDataProperty();

    ListProperty<String> placesProperty();

    ListProperty<DayOfWeek> daysProperty();

    ListProperty<LocalTime> timesProperty();
}
