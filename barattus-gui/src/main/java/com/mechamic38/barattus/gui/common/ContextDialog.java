/*
 * Boomega - A modern book explorer & catalog application
 * Copyright (C) 2020-2022  Daniel Gyoerffy
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.mechamic38.barattus.gui.common;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;

import java.util.function.Consumer;

/**
 * A {@link ContextDialog} is an abstract structure that represents a dialog
 * that is shown through a {@link Context}.
 */
public interface ContextDialog {

    ObjectProperty<EventHandler<Event>> onShownProperty();

    EventHandler<Event> getOnShown();

    void setOnShown(EventHandler<Event> value);

    ObjectProperty<EventHandler<Event>> onHiddenProperty();

    EventHandler<Event> getOnHidden();

    void setOnHidden(EventHandler<Event> value);

    Type getType();

    ObservableList<ButtonType> getButtonTypes();

    //

    BooleanProperty maximizedProperty();

    //

    boolean isMaximized();

    void setMaximized(boolean max);

    ObjectProperty<Node> contentProperty();

    //

    Node getContent();

    void setContent(Node content);

    StringProperty titleProperty();

    //

    String getTitle();

    void setTitle(String title);

    ObservableList<String> getStyleClass();

    //

    ObjectProperty<Exception> exceptionProperty();

    //

    Exception getException();

    void setException(Exception ex);

    String getDetails();

    //

    void setDetails(String details);

    StringProperty detailsProperty();

    BooleanProperty blockingProperty();

    //

    boolean isBlocking();

    void setBlocking(boolean blocking);

    Consumer<ButtonType> getOnResult();

    //

    void setOnResult(Consumer<ButtonType> onResult);

    ObjectProperty<Consumer<ButtonType>> onResultProperty();

    ReadOnlyBooleanProperty showingProperty();

    //

    boolean isShowing();

    /**
     * The types of a {@link ContextDialog}.
     */
    enum Type {
        INFORMATION,
        ERROR,
        WARNING,
        CONFIRMATION
    }
}
