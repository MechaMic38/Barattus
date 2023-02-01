package com.mechamic38.barattus.gui.trade;

import com.mechamic38.barattus.gui.common.ViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

public interface ITradeEditorViewModel extends ViewModel {
    StringProperty errorProperty();

    BooleanProperty editTurnProperty();

    BooleanProperty unconfirmedProperty();

    BooleanProperty ongoingProperty();
}
