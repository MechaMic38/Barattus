package com.mechamic38.barattus.core.tradeparams;

import com.mechamic38.barattus.core.tradeparams.HourInterval;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

public class HourIntervalTest {

    public static Stream<Arguments> provideIntervalsForTimeExtraction() {
        return Stream.of(
                Arguments.of(
                        new HourInterval(LocalTime.parse("20:00"), LocalTime.parse("21:00")),
                        List.of(
                                LocalTime.parse("20:00"),
                                LocalTime.parse("20:30"),
                                LocalTime.parse("21:00")
                        )
                ),
                Arguments.of(
                        new HourInterval(LocalTime.parse("17:59"), LocalTime.parse("18:59")),
                        List.of(
                                LocalTime.parse("18:00"),
                                LocalTime.parse("18:30")
                        )
                ),
                Arguments.of(
                        new HourInterval(LocalTime.parse("20:01"), LocalTime.parse("20:29")),
                        List.of()
                )
        );
    }

    public static Stream<Arguments> provideIntervalsForOverlapping() {
        return Stream.of(
                Arguments.of(
                        new HourInterval(LocalTime.parse("20:00"), LocalTime.parse("21:30")),
                        false
                ),
                Arguments.of(
                        new HourInterval(LocalTime.parse("15:00"), LocalTime.parse("17:00")),
                        false
                ),
                Arguments.of(
                        new HourInterval(LocalTime.parse("19:30"), LocalTime.parse("21:30")),
                        false
                ),
                Arguments.of(
                        new HourInterval(LocalTime.parse("18:00"), LocalTime.parse("19:45")),
                        true
                ),
                Arguments.of(
                        new HourInterval(LocalTime.parse("16:00"), LocalTime.parse("18:30")),
                        true
                ),
                Arguments.of(
                        new HourInterval(LocalTime.parse("17:30"), LocalTime.parse("18:00")),
                        true
                ),
                Arguments.of(
                        new HourInterval(LocalTime.parse("16:00"), LocalTime.parse("20:00")),
                        true
                )
        );
    }

    @Test
    public void startTime_cannot_be_after_endTime() {
        Executable action = () -> new HourInterval(LocalTime.NOON, LocalTime.MIDNIGHT);

        Assertions.assertThrows(IllegalArgumentException.class, action);
    }

    @ParameterizedTest
    @MethodSource("provideIntervalsForTimeExtraction")
    public void extracts_correct_half_and_full_hours(HourInterval interval, List<LocalTime> expected) {
        Assertions.assertEquals(expected, interval.getHalfAndFullTimes());
    }

    @ParameterizedTest
    @MethodSource("provideIntervalsForOverlapping")
    public void checks_overlapping_intervals_correctly(HourInterval interval, boolean expected) {
        var hourInterval = new HourInterval(
                LocalTime.parse("17:00"),
                LocalTime.parse("19:30")
        );

        Assertions.assertEquals(expected, interval.overlapsInterval(hourInterval));
    }
}
