package com.mechamic38.barattus.gui.offer;

import com.mechamic38.barattus.core.offer.Offer;
import com.mechamic38.barattus.gui.common.ViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

public interface IOfferDetailsViewModel extends ViewModel {
    boolean withdrawOffer();

    ObjectProperty<Offer> offerProperty();

    BooleanProperty ownerProperty();

    BooleanProperty withdrawableProperty();

    BooleanProperty proposableProperty();

    StringProperty errorProperty();
}
