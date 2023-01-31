package com.mechamic38.barattus.gui.tradeparams;

import com.mechamic38.barattus.core.tradeparams.HourInterval;
import com.mechamic38.barattus.gui.common.ViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.StringProperty;

import java.time.DayOfWeek;

public interface ITradeParamsViewModel extends ViewModel {

    boolean saveChanges();

    void addPlace(String place);

    void removePlace(String place);

    void addDay(DayOfWeek day);

    void removeDay(DayOfWeek day);

    void addInterval(Integer startHour, Integer startMinute, Integer endHour, Integer endMinute);

    void removeInterval(HourInterval interval);

    boolean loadFromFile(String path);

    BooleanProperty adminProperty();

    StringProperty errorProperty();

    StringProperty squareProperty();

    StringProperty expirationProperty();

    ListProperty<String> placesProperty();

    ListProperty<DayOfWeek> daysProperty();

    ListProperty<HourInterval> intervalsProperty();
}
