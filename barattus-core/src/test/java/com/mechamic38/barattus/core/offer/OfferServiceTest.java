package com.mechamic38.barattus.core.offer;

import com.mechamic38.barattus.core.category.Category;
import com.mechamic38.barattus.core.user.User;
import com.mechamic38.barattus.core.user.UserRole;
import com.mechamic38.barattus.util.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class OfferServiceTest {

    IOfferRepository offerRepository;
    IOfferLogRepository offerLogRepository;
    IOfferService offerService;

    @BeforeEach
    public void init() {
        offerRepository = new OfferRepositoryMock();
        offerLogRepository = new OfferLogRepositoryMock();
        offerService = new OfferService(offerRepository, offerLogRepository);
    }

    @Test
    void should_create_offer() {
        User user = new User("MechaMic_38", "666", UserRole.END_USER);
        Category category = new Category("Sport", "Bici", "Sport", "Biciclette");
        Result<Offer> result = offerService.createOffer(user, category, "MTB Hardtail 29", new LinkedList<>());

        Assertions.assertFalse(result.isError());

        Offer offer = result.getResult();
        Assertions.assertNotNull(offerRepository.getById(offer.getID()));
        Assertions.assertEquals(
                OfferStatus.OPEN,
                offerRepository.getById(offer.getID()).getStatus()
        );
    }

    @Test
    void should_withdraw_offer() {
        User user = new User("MechaMic_38", "666", UserRole.END_USER);
        Category category = new Category("Sport", "Bici", "Sport", "Biciclette");
        Result<Offer> result = offerService.createOffer(user, category, "MTB Hardtail 29", new LinkedList<>());

        Offer offer = result.getResult();
        result = offerService.withdrawOffer(offer, user);

        Assertions.assertFalse(result.isError());
        Assertions.assertEquals(
                OfferStatus.WITHDRAWN,
                offerRepository.getById(offer.getID()).getStatus()
        );
    }

    @Test
    void should_update_offer_status() {
        User user = new User("MechaMic_38", "666", UserRole.END_USER);
        Category category = new Category("Sport", "Bici", "Sport", "Biciclette");
        Result<Offer> result = offerService.createOffer(user, category, "MTB Hardtail 29", new LinkedList<>());

        Offer offer = result.getResult();
        result = offerService.updateOfferStatus(offer, OfferStatus.CLOSED, LocalDateTime.now());

        Assertions.assertFalse(result.isError());
        Assertions.assertEquals(
                OfferStatus.CLOSED,
                offerRepository.getById(offer.getID()).getStatus()
        );
    }
}
