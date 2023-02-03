package com.mechamic38.barattus.core.offer;

import com.mechamic38.barattus.core.offer.IOfferRepository;
import com.mechamic38.barattus.core.offer.Offer;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class OfferRepositoryMock implements IOfferRepository {

    private final List<Offer> offers;

    public OfferRepositoryMock() {
        this.offers = new LinkedList<>();
    }

    @Override
    public Offer getById(UUID id) {
        return offers.stream()
                .filter(offer -> offer.checkID(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Offer> getAll() {
        return offers;
    }

    @Override
    public void save(Offer offer) {
        if (!this.offers.contains(offer)) {
            this.offers.add(offer);
        }
    }

    @Override
    public void loadFromDataSource() {

    }
}
