package com.mechamic38.barattus.core.offer;

public class OfferService implements IOfferService {

    private final IOfferRepository offerRepository;
    private final IOfferLogRepository offerLogRepository;

    public OfferService(IOfferRepository offerRepository, IOfferLogRepository offerLogRepository) {
        this.offerRepository = offerRepository;
        this.offerLogRepository = offerLogRepository;
    }
}
