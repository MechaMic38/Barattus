package com.mechamic38.barattus.core.tradeparams;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class TradeParamsTest {

    @Test
    public void cannot_set_square_if_already_set() {
        var tradeParams = TradeParams.getDefaultParams();
        tradeParams.setSquare("Trapani");

        Executable action = () -> tradeParams.setSquare("Brescia");

        Assertions.assertThrows(IllegalArgumentException.class, action);
    }

    @Test
    public void expiration_days_must_not_be_negative_or_null() {
        var tradeParams = TradeParams.getDefaultParams();

        Executable successAction = () -> tradeParams.setExpirationDays(1);
        Executable failAction = () -> tradeParams.setExpirationDays(0);

        Assertions.assertDoesNotThrow(successAction);
        Assertions.assertThrows(IllegalArgumentException.class, failAction);
    }

    @Test
    public void cannot_add_already_existing_place() {
        var tradeParams = TradeParams.getDefaultParams();
        var place = "Lungomare";
        tradeParams.addPlace(place);

        Executable action = () -> tradeParams.addPlace(place);

        Assertions.assertThrows(IllegalArgumentException.class, action);
    }

    @Test
    public void cannot_add_already_existing_day() {
        var tradeParams = TradeParams.getDefaultParams();
        var day = DayOfWeek.MONDAY;
        tradeParams.addDay(day);

        Executable action = () -> tradeParams.addDay(day);

        Assertions.assertThrows(IllegalArgumentException.class, action);
    }

    @Test
    public void cannot_add_already_existing_hour_interval() {
        var tradeParams = TradeParams.getDefaultParams();
        var hourInterval = new HourInterval(
                LocalTime.MIDNIGHT,
                LocalTime.NOON
        );
        tradeParams.addHourInterval(hourInterval);

        Executable action = () -> tradeParams.addHourInterval(hourInterval);

        Assertions.assertThrows(IllegalArgumentException.class, action);
    }

    @Test
    public void cannot_add_interval_with_no_extractable_times() {
        var tradeParams = TradeParams.getDefaultParams();

        Executable action = () -> tradeParams.addHourInterval(
                new HourInterval(LocalTime.parse("13:01"), LocalTime.parse("13:29"))
        );

        Assertions.assertThrows(IllegalArgumentException.class, action);
    }

    @Test
    public void cannot_add_interval_overlapping_existing_one() {
        var tradeParams = TradeParams.getDefaultParams();
        var hourInterval = new HourInterval(
                LocalTime.MIDNIGHT,
                LocalTime.NOON
        );
        tradeParams.addHourInterval(hourInterval);

        Executable successAction = () -> tradeParams.addHourInterval(
                new HourInterval(LocalTime.parse("13:00"), LocalTime.parse("14:00"))
        );
        Executable failAction = () -> tradeParams.addHourInterval(
                new HourInterval(LocalTime.parse("11:00"), LocalTime.parse("14:00"))
        );

        Assertions.assertDoesNotThrow(successAction);
        Assertions.assertThrows(IllegalArgumentException.class, failAction);
    }
}
