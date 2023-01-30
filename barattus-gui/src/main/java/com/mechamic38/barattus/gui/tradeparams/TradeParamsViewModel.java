package com.mechamic38.barattus.gui.tradeparams;

import com.mechamic38.barattus.core.tradeparams.HourInterval;
import com.mechamic38.barattus.core.tradeparams.ITradeParamRepository;
import com.mechamic38.barattus.core.tradeparams.TradeParams;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class TradeParamsViewModel implements ITradeParamsViewModel {

    private final ITradeParamRepository tradeParamRepository;

    private final ObjectProperty<TradeParams> tradeParams = new SimpleObjectProperty<>(null);
    private final StringProperty errorMessage = new SimpleStringProperty("");

    public TradeParamsViewModel(ITradeParamRepository tradeParamRepository) {
        this.tradeParamRepository = tradeParamRepository;
        tradeParams.set(tradeParamRepository.get());
    }

    @Override
    public void saveChanges() {
        TradeParams params = tradeParams.get();
        tradeParamRepository.save(params);
    }

    @Override
    public void addPlace(String place) {
        TradeParams params = tradeParams.get();
        try {
            params.addPlace(place);
        } catch (IllegalArgumentException e) {
            errorMessage.set(e.getMessage());
        }
        tradeParams.set(params);
    }

    @Override
    public void removePlace(String place) {
        TradeParams params = tradeParams.get();
        params.removePlace(place);
        tradeParams.set(params);
    }

    @Override
    public void addDay(DayOfWeek day) {
        TradeParams params = tradeParams.get();
        try {
            params.addDay(day);
        } catch (IllegalArgumentException e) {
            errorMessage.set(e.getMessage());
        }
        tradeParams.set(params);
    }

    @Override
    public void removeDay(DayOfWeek day) {
        TradeParams params = tradeParams.get();
        params.removeDay(day);
        tradeParams.set(params);
    }

    @Override
    public void addInterval(Integer startHour, Integer startMinute, Integer endHour, Integer endMinute) {
        TradeParams params = tradeParams.get();
        try {
            HourInterval interval = new HourInterval(
                    LocalTime.of(startHour, startMinute),
                    LocalTime.of(endHour, endMinute)
            );
            params.addHourInterval(interval);
        } catch (IllegalArgumentException e) {
            errorMessage.set(e.getMessage());
        }
        tradeParams.set(params);
    }

    @Override
    public void removeInterval(HourInterval interval) {
        TradeParams params = tradeParams.get();
        params.removeHourInterval(interval);
        tradeParams.set(params);
    }

    @Override
    public void loadFromFile(String path) {
        //TODO Load from file
    }
}
