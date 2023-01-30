package com.mechamic38.barattus.gui.tradeparams;

import com.mechamic38.barattus.core.tradeparams.HourInterval;
import com.mechamic38.barattus.gui.common.ViewModel;

import java.time.DayOfWeek;

public interface ITradeParamsViewModel extends ViewModel {

    void saveChanges();

    void addPlace(String place);

    void removePlace(String place);

    void addDay(DayOfWeek day);

    void removeDay(DayOfWeek day);

    void addInterval(Integer startHour, Integer startMinute, Integer endHour, Integer endMinute);

    void removeInterval(HourInterval interval);

    void loadFromFile(String path);
}
