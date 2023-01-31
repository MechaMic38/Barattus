package com.mechamic38.barattus.core.trade;

import com.mechamic38.barattus.core.offer.IOfferRepository;
import com.mechamic38.barattus.core.offer.IOfferService;
import com.mechamic38.barattus.core.offer.Offer;
import com.mechamic38.barattus.core.offer.OfferStatus;
import com.mechamic38.barattus.core.tradeparams.ITradeParamRepository;
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

    private boolean checkDifferentUsers(String initiatorID, String proposedID) {
        return !initiatorID.equals(proposedID);
    }

    private boolean checkCompatibleCategories(String initiatorCategory, String proposedCategory) {
        return initiatorCategory.equals(proposedCategory);
    }
}
