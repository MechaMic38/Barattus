package com.mechamic38.barattus.core.offer;

import com.mechamic38.barattus.core.category.Category;
import com.mechamic38.barattus.core.user.User;
import com.mechamic38.barattus.util.Result;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OfferService implements IOfferService {

    private final IOfferRepository offerRepository;
    private final IOfferLogRepository offerLogRepository;

    public OfferService(IOfferRepository offerRepository, IOfferLogRepository offerLogRepository) {
        this.offerRepository = offerRepository;
        this.offerLogRepository = offerLogRepository;
    }

    /**
     * Creates a new offer.
     *
     * @param user        User that publishes the offer
     * @param category    Category the offer belongs to
     * @param title       Title of the offer
     * @param offerFields Fields of the offer
     * @return Result of the operation
     */
    @Override
    public Result<Offer> createOffer(User user, Category category, String title, List<OfferField> offerFields) {
        LocalDateTime time = LocalDateTime.now();
        Offer offer = new Offer(
                UUID.randomUUID(),
                user.getID(),
                category.getID(),
                title,
                time
        );
        offer.addOfferFields(offerFields);
        offer.setStatus(OfferStatus.OPEN);

        OfferLog offerLog = new OfferLog(
                UUID.randomUUID(),
                time,
                offer.getID(),
                OfferStatus.OPEN
        );

        offerRepository.save(offer);
        offerLogRepository.save(offerLog);
        return Result.success(offer);
    }

    @Override
    public Result<Offer> updateOfferStatus(Offer offer, OfferStatus status, LocalDateTime time) {
        offer.setStatus(status);

        OfferLog offerLog = new OfferLog(
                UUID.randomUUID(),
                time,
                offer.getID(),
                status
        );

        offerRepository.save(offer);
        offerLogRepository.save(offerLog);
        return Result.success(offer);
    }

    //TODO Remove this crap

    @Override
    public List<Offer> getCompatibleOffers(Offer offer, User user) {
        return this.getCompatibleOffers(offer).stream()
                .filter(compatible -> compatible.getUserID().equals(user.getID()))
                .collect(Collectors.toList());
    }

    private List<Offer> getCompatibleOffers(Offer offer) {
        return this.getOpenOffersByCategory(offer.getCategoryID()).stream()
                .filter(compatible -> !compatible.equals(offer))
                .collect(Collectors.toList());
    }

    @Override
    public List<Offer> getOpenOffersByCategory(Category category) {
        return offerRepository.getAll().stream()
                .filter(offer -> offer.getCategoryID().equals(category.getID()))
                .filter(offer -> offer.getStatus() == OfferStatus.OPEN)
                .collect(Collectors.toList());
    }

    private List<Offer> getOpenOffersByCategory(String categoryID) {
        return offerRepository.getAll().stream()
                .filter(offer -> offer.getCategoryID().equals(categoryID))
                .filter(offer -> offer.getStatus() == OfferStatus.OPEN)
                .collect(Collectors.toList());
    }

    @Override
    public List<Offer> getOpenClosedInExchangeOffersByCategory(Category category) {
        return offerRepository.getAll().stream()
                .filter(offer -> offer.getCategoryID().equals(category.getID()))
                .filter(tradeOffer -> tradeOffer.getStatus() == OfferStatus.OPEN ||
                        tradeOffer.getStatus() == OfferStatus.CLOSED ||
                        tradeOffer.getStatus() == OfferStatus.IN_EXCHANGE)
                .collect(Collectors.toList());
    }

    @Override
    public List<Offer> getOffersByCategoryAndUser(Category category, User user) {
        return offerRepository.getAll().stream()
                .filter(offer -> offer.getCategoryID().equals(category.getID()))
                .filter(offer -> offer.getUserID().equals(user.getID()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Offer> getOffersByUser(User user) {
        return offerRepository.getAll().stream()
                .filter(offer -> offer.getUserID().equals(user.getID()))
                .collect(Collectors.toList());
    }
}
