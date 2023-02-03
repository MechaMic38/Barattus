package com.mechamic38.barattus.core.trade;

import com.mechamic38.barattus.core.offer.IOfferRepository;
import com.mechamic38.barattus.core.offer.IOfferService;
import com.mechamic38.barattus.core.offer.Offer;
import com.mechamic38.barattus.core.offer.OfferStatus;
import com.mechamic38.barattus.core.tradeparams.ITradeParamRepository;
import com.mechamic38.barattus.core.tradeparams.TradeParams;
import com.mechamic38.barattus.core.user.User;
import com.mechamic38.barattus.util.Result;

import java.time.LocalDateTime;
import java.util.UUID;

public class TradeService implements ITradeService {

    private final ITradeRepository tradeRepository;
    private final IOfferService offerService;
    private final IOfferRepository offerRepository;
    private final ITradeParamRepository tradeParamRepository;

    public TradeService(ITradeRepository tradeRepository, IOfferService offerService,
                        IOfferRepository offerRepository, ITradeParamRepository tradeParamRepository) {
        this.tradeRepository = tradeRepository;
        this.offerService = offerService;
        this.offerRepository = offerRepository;
        this.tradeParamRepository = tradeParamRepository;
    }

    /**
     * Creates a trade with the given offers.
     *
     * @param initiatorOffer Offer that initiates the trade
     * @param proposedOffer  Offer that gets proposed for the trade
     * @return Result of the operation
     */
    @Override
    public Result<Trade> createTrade(Offer initiatorOffer, Offer proposedOffer) {
        if (!checkDifferentUsers(initiatorOffer.getUserID(), proposedOffer.getUserID())) {
            return Result.error("trade.create.fail");
        }
        if (!checkCompatibleCategories(initiatorOffer.getCategoryID(), proposedOffer.getCategoryID())) {
            return Result.error("trade.create.fail");
        }

        LocalDateTime time = LocalDateTime.now();

        Trade trade = new Trade(
                UUID.randomUUID(),
                initiatorOffer.getUserID(),
                proposedOffer.getUserID(),
                initiatorOffer.getID(),
                proposedOffer.getID()
        );
        trade.setLastUpdate(time);
        trade.setStatus(TradeStatus.UNCONFIRMED);

        offerService.updateOfferStatus(initiatorOffer, OfferStatus.COUPLED, time);
        offerService.updateOfferStatus(proposedOffer, OfferStatus.SELECTED, time);

        tradeRepository.save(trade);
        return Result.success(trade);
    }

    /**
     * Rejects a trade.
     *
     * @param trade Trade to reject
     * @param user  User requesting the rejection
     * @return Result of the operation
     */
    @Override
    public Result<Trade> rejectTrade(Trade trade, User user) {
        if (!trade.getProposedUserID().equals(user.getID())) {
            return Result.error("trade.reject.fail");
        }

        LocalDateTime time = LocalDateTime.now();
        trade.setLastUpdate(time);
        trade.setStatus(TradeStatus.REJECTED);

        Offer initiatorOffer = offerRepository.getById(trade.getInitiatorOfferID());
        Offer proposedOffer = offerRepository.getById(trade.getProposedOfferID());

        offerService.updateOfferStatus(initiatorOffer, OfferStatus.OPEN, time);
        offerService.updateOfferStatus(proposedOffer, OfferStatus.OPEN, time);

        tradeRepository.save(trade);
        return Result.success(trade);
    }

    /**
     * Confirms a trade.
     *
     * @param trade   Trade to confirm
     * @param details Updated details of the trade
     * @param user    Requesting user
     * @return Result of the operation
     */
    @Override
    public Result<Trade> confirmTrade(Trade trade, TradeDetails details, User user) {
        Result<User> validUser;
        if ((validUser = checkUserEditTurn(trade, user)).isError()) {
            return Result.error(validUser.getError());
        }

        Result<TradeDetails> validDetails;
        if ((validDetails = checkValidTradeDetails(details)).isError()) {
            return Result.error(validDetails.getError());
        }

        LocalDateTime time = LocalDateTime.now();
        trade.setLastUpdate(time);
        trade.incrementEditedCount();
        trade.updateTradeDetails(details);
        trade.setStatus(TradeStatus.ONGOING);

        Offer initiatorOffer = offerRepository.getById(trade.getInitiatorOfferID());
        Offer proposedOffer = offerRepository.getById(trade.getProposedOfferID());

        offerService.updateOfferStatus(initiatorOffer, OfferStatus.IN_EXCHANGE, time);
        offerService.updateOfferStatus(proposedOffer, OfferStatus.IN_EXCHANGE, time);

        tradeRepository.save(trade);
        return Result.success(trade);
    }

    /**
     * Accepts a trade.
     *
     * @param trade Trade to accept
     * @param user  Requesting user
     * @return Result of the operation
     */
    @Override
    public Result<Trade> acceptTrade(Trade trade, User user) {
        Result<User> validUser;
        if ((validUser = checkUserEditTurn(trade, user)).isError()) {
            return Result.error(validUser.getError());
        }

        LocalDateTime time = LocalDateTime.now();
        trade.setLastUpdate(time);
        trade.setStatus(TradeStatus.ACCEPTED);

        Offer initiatorOffer = offerRepository.getById(trade.getInitiatorOfferID());
        Offer proposedOffer = offerRepository.getById(trade.getProposedOfferID());

        offerService.updateOfferStatus(initiatorOffer, OfferStatus.CLOSED, time);
        offerService.updateOfferStatus(proposedOffer, OfferStatus.CLOSED, time);

        tradeRepository.save(trade);
        return Result.success(trade);
    }

    /**
     * Edits the trade.
     *
     * @param trade   Trade to edit
     * @param details Updated trade details
     * @param user    Requesting user
     * @return Result of the operation
     */
    @Override
    public Result<Trade> editTradeDetails(Trade trade, TradeDetails details, User user) {
        Result<User> validUser;
        if ((validUser = checkUserEditTurn(trade, user)).isError()) {
            return Result.error(validUser.getError());
        }

        Result<TradeDetails> validDetails;
        if ((validDetails = checkValidTradeDetails(details)).isError()) {
            return Result.error(validDetails.getError());
        }

        LocalDateTime time = LocalDateTime.now();
        trade.setLastUpdate(time);
        trade.incrementEditedCount();
        trade.updateTradeDetails(details);

        tradeRepository.save(trade);
        return Result.success(trade);
    }

    private Result<User> checkUserEditTurn(Trade trade, User user) {
        if (!trade.hasParticipant(user.getID())) {
            return Result.error("trade.user.error");
        } else if (!trade.getEditTurnUser().equals(user.getID())) {
            return Result.error("trade.user.turn.error");
        }

        return Result.success(user);
    }

    private Result<TradeDetails> checkValidTradeDetails(TradeDetails details) {
        TradeParams params = tradeParamRepository.get();

        if (!params.getPlaces().contains(details.place)) {
            return Result.error("trade.details.wrong.place");
        } else if (!params.getDays().contains(details.day)) {
            return Result.error("trade.details.wrong.day");
        } else if (!params.getAllowedTimes().contains(details.time)) {
            return Result.error("trade.details.wrong.time");
        }

        return Result.success(details);
    }

    private boolean checkDifferentUsers(String initiatorID, String proposedID) {
        return !initiatorID.equals(proposedID);
    }

    private boolean checkCompatibleCategories(String initiatorCategory, String proposedCategory) {
        return initiatorCategory.equals(proposedCategory);
    }
}
