package com.mechamic38.barattus.core.usecase;

import com.mechamic38.barattus.core.offer.Offer;

import java.util.List;
import java.util.function.Function;

public interface IQueryOffersUseCase extends Function<OfferQuery, List<Offer>> {
}
