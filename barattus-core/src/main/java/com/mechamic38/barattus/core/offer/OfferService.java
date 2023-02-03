package com.mechamic38.barattus.core.offer;

import com.mechamic38.barattus.core.category.Category;
import com.mechamic38.barattus.core.user.User;
import com.mechamic38.barattus.util.Result;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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

        return updateOfferStatus(
                offer,
                OfferStatus.OPEN,
                time
        );
    }

    /**
     * Withdraws an offer
     *
     * @param offer Offer to withdraw
     * @param user  Requesting user
     * @return Result of the operation
     */
    @Override
    public Result<Offer> withdrawOffer(Offer offer, User user) {
        if (!offer.canWithdraw(user)) {
            return Result.error("offer.withdraw.fail");
        }

        return updateOfferStatus(
                offer,
                OfferStatus.WITHDRAWN,
                LocalDateTime.now()
        );
    }

    /**
     * Updates an offer status.
     *
     * @param offer  Offer to update
     * @param status New offer status
     * @param time   Time of the update
     * @return Result of the operation
     */
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
}
