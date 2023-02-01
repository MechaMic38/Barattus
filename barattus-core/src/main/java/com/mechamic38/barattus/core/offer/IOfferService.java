package com.mechamic38.barattus.core.offer;

import com.mechamic38.barattus.core.category.Category;
import com.mechamic38.barattus.core.user.User;
import com.mechamic38.barattus.util.Result;

import java.time.LocalDateTime;
import java.util.List;

public interface IOfferService {
    Result<Offer> createOffer(User user, Category category, String title, List<OfferField> offerFields);

    Result<Offer> withdrawOffer(Offer offer, User user);

    Result<Offer> updateOfferStatus(Offer offer, OfferStatus status, LocalDateTime time);

    List<Offer> getCompatibleOffers(Offer offer, User user);

    List<Offer> getOpenOffersByCategory(Category category);

    List<Offer> getOpenClosedInExchangeOffersByCategory(Category category);

    List<Offer> getOffersByCategoryAndUser(Category category, User user);

    List<Offer> getOffersByUser(User user);
}
