package com.mechamic38.barattus.gui.trade;

import com.mechamic38.barattus.core.offer.Offer;
import com.mechamic38.barattus.core.usecase.OfferData;
import com.mechamic38.barattus.gui.common.ViewModel;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

public interface ISelectTradeOfferViewModel extends ViewModel {
    boolean proposeTrade(Offer otherOffer);

    StringProperty errorProperty();

    ObjectProperty<OfferData> otherOfferProperty();

    ListProperty<Offer> offersProperty();
}
