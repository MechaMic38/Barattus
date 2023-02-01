package com.mechamic38.barattus.gui.util;

import com.mechamic38.barattus.core.category.Category;
import com.mechamic38.barattus.core.tradeparams.HourInterval;
import com.mechamic38.barattus.i18n.api.I18N;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;

public class GUIUtils {

    public static String convertCategoryName(Category category) {
        return category.getHierarchyName() + " => " + category.getName();
    }

    public static String convertHourInterval(HourInterval interval) {
        return convertLocalTime(interval.startTime()) + " - " + convertLocalTime(interval.endTime());
    }

    public static String convertLocalDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public static String convertLocalTime(LocalTime time) {
        return time.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }

    public static String convertDayOfWeek(DayOfWeek day) {
        return day.getDisplayName(TextStyle.FULL, I18N.getLocale());
    }
}
