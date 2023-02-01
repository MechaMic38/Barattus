package com.mechamic38.barattus.gui.offer;

import com.mechamic38.barattus.core.offer.IOfferService;
import com.mechamic38.barattus.core.offer.Offer;
import com.mechamic38.barattus.core.usecase.IGetOfferDataUseCase;
import com.mechamic38.barattus.core.usecase.OfferData;
import com.mechamic38.barattus.gui.common.SessionState;
import com.mechamic38.barattus.util.Result;
import javafx.beans.property.*;

public class OfferDetailsViewModel implements IOfferDetailsViewModel {

    private final IOfferService offerService;
    private final IGetOfferDataUseCase getOfferDataUseCase;

    private final ObjectProperty<OfferData> offer = new SimpleObjectProperty<>();
    private final BooleanProperty owner = new SimpleBooleanProperty(false);
    private final BooleanProperty withdrawable = new SimpleBooleanProperty(false);
    private final BooleanProperty proposable = new SimpleBooleanProperty(false);
    private final StringProperty error = new SimpleStringProperty("");

    public OfferDetailsViewModel(IOfferService offerService, IGetOfferDataUseCase getOfferDataUseCase) {
        this.offerService = offerService;
        this.getOfferDataUseCase = getOfferDataUseCase;
    }

    @Override
    public boolean withdrawOffer() {
        Result<Offer> result = offerService.withdrawOffer(
                offer.get().getOffer(),
                SessionState.getInstance().getUser()
        );

        if (result.isError()) {
            errorProperty().set(result.getError());
            return false;
        } else {
            setOfferProperties(result.getResult());
            return true;
        }
    }

    @Override
    public void initialize() {
        setOfferProperties(
                SessionState.getInstance().getOffer()
        );
        owner.set(offer.get().getOffer().isOwner(
                SessionState.getInstance().getUser()
        ));
        withdrawable.set(offer.get().getOffer().canWithdraw(
                SessionState.getInstance().getUser()
        ));
        proposable.set(offer.get().getOffer().canPropose(
                SessionState.getInstance().getUser()
        ));
    }

    private void setOfferProperties(Offer offer) {
        this.offer.set(getOfferDataUseCase.apply(offer));
        owner.set(this.offer.get().getOffer().isOwner(
                SessionState.getInstance().getUser()
        ));
        withdrawable.set(this.offer.get().getOffer().canWithdraw(
                SessionState.getInstance().getUser()
        ));
        proposable.set(this.offer.get().getOffer().canPropose(
                SessionState.getInstance().getUser()
        ));
    }

    @Override
    public ObjectProperty<OfferData> offerProperty() {
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
