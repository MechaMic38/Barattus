package com.mechamic38.barattus.gui.offer;

import com.mechamic38.barattus.core.offer.IOfferRepository;
import com.mechamic38.barattus.core.offer.IOfferService;

public class OfferDetailsViewModel implements IOfferDetailsViewModel {

    private final IOfferService offerService;
    private final IOfferRepository offerRepository;

    public OfferDetailsViewModel(IOfferService offerService, IOfferRepository offerRepository) {
        this.offerService = offerService;
        this.offerRepository = offerRepository;
    }
}
