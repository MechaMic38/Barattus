package com.mechamic38.barattus.gui.tradeparams;

import com.mechamic38.barattus.core.tradeparams.HourInterval;
import com.mechamic38.barattus.core.tradeparams.ITradeParamRepository;
import com.mechamic38.barattus.core.tradeparams.ITradeParamsService;
import com.mechamic38.barattus.core.tradeparams.TradeParams;
import com.mechamic38.barattus.core.user.UserRole;
import com.mechamic38.barattus.gui.common.SessionState;
import com.mechamic38.barattus.util.ListUtils;
import com.mechamic38.barattus.util.Result;
import javafx.beans.property.*;
import javafx.collections.FXCollections;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class TradeParamsViewModel implements ITradeParamsViewModel {

    private final ITradeParamsService tradeParamsService;
    private final ITradeParamRepository tradeParamRepository;
    private final BooleanProperty admin = new SimpleBooleanProperty(false);
    private final StringProperty error = new SimpleStringProperty("");
    private final StringProperty square = new SimpleStringProperty();
    private final StringProperty expiration = new SimpleStringProperty();
    private final ListProperty<String> places = new SimpleListProperty<>();
    private final ListProperty<DayOfWeek> days = new SimpleListProperty<>();
    private final ListProperty<HourInterval> intervals = new SimpleListProperty<>();
    private TradeParams tradeParams;

    public TradeParamsViewModel(ITradeParamsService tradeParamsService, ITradeParamRepository tradeParamRepository) {
        this.tradeParamsService = tradeParamsService;
        this.tradeParamRepository = tradeParamRepository;

        setProperties();
    }

    private void setProperties() {
        admin.set(
                SessionState.getInstance().getUser().getRole().equals(UserRole.CONFIGURATOR)
        );
    }

    @Override
    public boolean saveChanges() {
        try {
            tradeParams.setSquare(square.get());
            tradeParams.setExpirationDays(Integer.parseInt(expiration.get()));
            tradeParamRepository.save(tradeParams);
            return true;
        } catch (IllegalArgumentException e) {
            error.set(e.getMessage());
        }
        return false;
    }

    @Override
    public void addPlace(String place) {
        try {
            tradeParams.addPlace(place);
            places.add(place);
        } catch (IllegalArgumentException e) {
            error.set(e.getMessage());
        }
    }

    @Override
    public void removePlace(String place) {
        tradeParams.removePlace(place);
        places.remove(place);
    }

    @Override
    public void addDay(DayOfWeek day) {
        try {
            tradeParams.addDay(day);
            days.add(day);
        } catch (IllegalArgumentException e) {
            error.set(e.getMessage());
        }
    }

    @Override
    public void removeDay(DayOfWeek day) {
        tradeParams.removeDay(day);
        days.remove(day);
    }

    @Override
    public void addInterval(Integer startHour, Integer startMinute, Integer endHour, Integer endMinute) {
        try {
            HourInterval interval = new HourInterval(
                    LocalTime.of(startHour, startMinute),
                    LocalTime.of(endHour, endMinute)
            );
            tradeParams.addHourInterval(interval);
            intervals.add(interval);
        } catch (IllegalArgumentException e) {
            error.set(e.getMessage());
        }
    }

    @Override
    public void removeInterval(HourInterval interval) {
        tradeParams.removeHourInterval(interval);
        intervals.remove(interval);
    }

    @Override
    public boolean loadFromFile(String path) {
        Result<TradeParams> result = tradeParamsService.loadParamsFromFile(path);

        if (result.isError()) {
            error.set(result.getError());
            return false;
        } else {
            tradeParams = result.getResult();
            setProperties();
            return true;
        }
    }

    @Override
    public BooleanProperty adminProperty() {
        return admin;
    }

    @Override
    public StringProperty errorProperty() {
        return error;
    }

    @Override
    public StringProperty squareProperty() {
        return square;
    }

    @Override
    public StringProperty expirationProperty() {
        return expiration;
    }

    @Override
    public ListProperty<String> placesProperty() {
        return places;
    }

    @Override
    public ListProperty<DayOfWeek> daysProperty() {
        return days;
    }

    @Override
    public ListProperty<HourInterval> intervalsProperty() {
        return intervals;
    }

    @Override
    public void initialize() {
        tradeParams = tradeParamRepository.get();

        square.set(tradeParams.getSquare());
        expiration.set(Integer.toString(tradeParams.getExpirationDays()));
        places.set(FXCollections.observableList(
                ListUtils.copy(tradeParams.getPlaces())
        ));
        days.set(FXCollections.observableList(
                ListUtils.copy(tradeParams.getDays())
        ));
        intervals.set(FXCollections.observableList(
                ListUtils.copy(tradeParams.getHourIntervals())
        ));
    }
}
