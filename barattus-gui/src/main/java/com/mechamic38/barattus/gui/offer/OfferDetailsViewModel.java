package com.mechamic38.barattus.gui.offer;

import com.mechamic38.barattus.core.offer.IOfferRepository;
import com.mechamic38.barattus.core.offer.IOfferService;
import com.mechamic38.barattus.core.offer.Offer;
import com.mechamic38.barattus.gui.common.SessionState;
import javafx.beans.property.*;

public class OfferDetailsViewModel implements IOfferDetailsViewModel {

    private final IOfferService offerService;
    private final IOfferRepository offerRepository;

    private final ObjectProperty<Offer> offer = new SimpleObjectProperty<>();
    private final BooleanProperty owner = new SimpleBooleanProperty(false);
    private final BooleanProperty withdrawable = new SimpleBooleanProperty(false);
    private final BooleanProperty proposable = new SimpleBooleanProperty(false);
    private final StringProperty error = new SimpleStringProperty("");

    public OfferDetailsViewModel(IOfferService offerService, IOfferRepository offerRepository) {
        this.offerService = offerService;
        this.offerRepository = offerRepository;
    }

    @Override
    public boolean withdrawOffer() {
        //TODO
        return false;
    }

    @Override
    public void initialize() {
        offer.set(SessionState.getInstance().getOffer());
        owner.set(offer.get().isOwner(
                SessionState.getInstance().getUser()
        ));
        withdrawable.set(offer.get().canWithdraw(
                SessionState.getInstance().getUser()
        ));
        proposable.set(offer.get().canPropose(
                SessionState.getInstance().getUser()
        ));
    }

    @Override
    public ObjectProperty<Offer> offerProperty() {
        return offer;
    }

    @Override
    public BooleanProperty ownerProperty() {
        return owner;
    }

    @Override
    public BooleanProperty withdrawableProperty() {
        return withdrawable;
    }

    @Override
    public BooleanProperty proposableProperty() {
        return proposable;
    }

    @Override
    public StringProperty errorProperty() {
        return error;
    }
}
