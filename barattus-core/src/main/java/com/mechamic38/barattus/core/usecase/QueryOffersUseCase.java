package com.mechamic38.barattus.core.usecase;

import com.mechamic38.barattus.core.offer.IOfferRepository;
import com.mechamic38.barattus.core.offer.Offer;

import java.util.List;

public class QueryOffersUseCase implements IQueryOffersUseCase {

    private final IOfferRepository offerRepository;

    public QueryOffersUseCase(IOfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public List<Offer> apply(OfferQuery offerQuery) {
        return offerRepository.getAll().stream()
                .filter(offer -> {
                    if (offerQuery.getCategory() != null) {
                        return offerQuery.getCategory().checkID(offer.getCategoryID());
                    } else {
                        return true;
                    }
                })
                .filter(offer -> {
                    if (offerQuery.getUser() != null) {
                        return offerQuery.getUser().checkID(offer.getUserID());
                    } else {
                        return true;
                    }
                })
                .filter(offer -> {
                    if (!offerQuery.getStatuses().isEmpty()) {
                        return offerQuery.getStatuses().contains(offer.getStatus());
                    } else {
                        return true;
                    }
                })
                .toList();
    }
}
