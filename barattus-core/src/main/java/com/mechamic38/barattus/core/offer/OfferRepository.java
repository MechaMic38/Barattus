package com.mechamic38.barattus.core.offer;

import com.mechamic38.barattus.persistence.offer.IOfferDataSource;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Offers repository.
 */
public class OfferRepository implements IOfferRepository {

    private final List<Offer> offers;
    private final IOfferDataSource dataSource;
    private final OfferMapper mapper;


    public OfferRepository(IOfferDataSource dataSource, OfferMapper mapper) {
        this.dataSource = dataSource;
        this.mapper = mapper;
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
    public void save(Offer offer) {
        if (!this.offers.contains(offer)) {
            this.offers.add(offer);
            dataSource.insert(mapper.toDto(offer));
        } else {
            dataSource.update(mapper.toDto(offer));
        }
    }
}
