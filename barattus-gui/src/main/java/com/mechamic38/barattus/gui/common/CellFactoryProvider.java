package com.mechamic38.barattus.gui.common;

import com.mechamic38.barattus.core.category.Category;
import com.mechamic38.barattus.core.category.CategoryField;
import com.mechamic38.barattus.core.tradeparams.HourInterval;
import com.mechamic38.barattus.i18n.api.I18N;
import javafx.scene.control.ListCell;

import java.time.DayOfWeek;
import java.time.format.TextStyle;

public class CellFactoryProvider {

    public static ListCell<Category> getCategoryBoxCell() {
        return new ListCell<>() {
            @Override
            protected void updateItem(Category category, boolean empty) {
                super.updateItem(category, empty);
                if (empty || category == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(category.getName());
                }
            }
        };
    }

    public static ListCell<CategoryField.Type> getCategoryFieldTypeBoxCell() {
        return new ListCell<>() {
            @Override
            protected void updateItem(CategoryField.Type type, boolean empty) {
                super.updateItem(type, empty);
                if (empty || type == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(I18N.getValue(type.i18n));
                }
            }
        };
    }

    public static ListCell<DayOfWeek> getDayBoxCell() {
        return new ListCell<>() {
            @Override
            protected void updateItem(DayOfWeek day, boolean empty) {
                super.updateItem(day, empty);
                if (empty || day == null) {
                    setText(null);
                } else {
                    setText(day.getDisplayName(
                            TextStyle.FULL,
                            I18N.getLocale()
                    ));
                }
            }

        };
    }

    public static ListCell<DayOfWeek> getDayListCell() {
        return new ListCell<>() {
            @Override
            protected void updateItem(DayOfWeek day, boolean empty) {
                super.updateItem(day, empty);
                if (empty || day == null) {
                    setText(null);
                } else {
                    setText(day.getDisplayName(
                            TextStyle.FULL,
                            I18N.getLocale()
                    ));
                }
            }
        };
    }

    public static ListCell<HourInterval> getIntervalListCell() {
        return new ListCell<>() {
            @Override
            protected void updateItem(HourInterval interval, boolean empty) {
                super.updateItem(interval, empty);
                if (empty || interval == null) {
                    setText(null);
                } else {
                    setText(interval.startTime().toString() + " - " + interval.endTime().toString());
                }
            }
        };
    }
}
