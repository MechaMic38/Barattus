package com.mechamic38.barattus.core.tradeparams;

import com.mechamic38.barattus.persistence.tradeparams.TradeParamDTO;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper class for trade params.
 */
public class TradeParamsMapper {

    /**
     * Converts the given TradeParams entity into a TradeParamsDTO object.
     *
     * @param tradeParams TradeParams entity to map
     * @return TradeParamsDTO object
     */
    public TradeParamDTO toDto(TradeParams tradeParams) {
        List<String> days = prepareDaysForDto(tradeParams.getDays());
        List<String> hourIntervals = prepareHourIntervalsForDto(tradeParams.getHourIntervals());

        return new TradeParamDTO(
                tradeParams.getSquare(),
                tradeParams.getPlaces(),
                days,
                hourIntervals,
                tradeParams.getExpirationDays()
        );
    }

    /**
     * Converts the given TradeParamsDTO object into a TradeParams entity.
     *
     * @param tradeParamsDTO TradeParamsDTO object to map
     * @return TradeParams entity
     */
    public TradeParams fromDto(TradeParamDTO tradeParamsDTO) {
        List<DayOfWeek> days = prepareDaysFromDto(tradeParamsDTO.getDays());
        List<HourInterval> hourIntervals = prepareHourIntervalsFromDto(tradeParamsDTO.getHourIntervals());

        return new TradeParams(
                tradeParamsDTO.getSquare(),
                tradeParamsDTO.getExpirationDays(),
                tradeParamsDTO.getPlaces(),
                days,
                hourIntervals
        );
    }

    private List<String> prepareDaysForDto(List<DayOfWeek> days) {
        return days.stream()
                .map(DayOfWeek::toString)
                .collect(Collectors.toList());
    }

    private List<String> prepareHourIntervalsForDto(List<HourInterval> hourIntervals) {
        return hourIntervals.stream()
                .map(hourInterval -> hourInterval.startTime().toString() + " - " + hourInterval.endTime().toString())
                .collect(Collectors.toList());
    }

    private List<DayOfWeek> prepareDaysFromDto(List<String> days) {
        return days.stream()
                .map(DayOfWeek::valueOf)
                .collect(Collectors.toList());
    }

    private List<HourInterval> prepareHourIntervalsFromDto(List<String> hourIntervals) {
        return hourIntervals.stream()
                .map(hourInterval -> {
                    String[] values = hourInterval.split(" - ");
                    return new HourInterval(
                            LocalTime.parse(values[0]),
                            LocalTime.parse(values[1])
                    );
                })
                .collect(Collectors.toList());
    }
}
