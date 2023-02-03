package com.mechamic38.barattus.core.usecase;

import com.mechamic38.barattus.core.offer.IOfferRepository;
import com.mechamic38.barattus.core.offer.IOfferService;
import com.mechamic38.barattus.core.offer.Offer;
import com.mechamic38.barattus.core.offer.OfferStatus;
import com.mechamic38.barattus.core.trade.ITradeRepository;
import com.mechamic38.barattus.core.trade.Trade;
import com.mechamic38.barattus.core.trade.TradeStatus;
import com.mechamic38.barattus.core.tradeparams.ITradeParamRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CheckTradesExpiryUseCase implements ICheckTradesExpiryUseCase {

    private final IQueryTradesUseCase queryTradesUseCase;
    private final IOfferService offerService;
    private final ITradeRepository tradeRepository;
    private final IOfferRepository offerRepository;
    private final ITradeParamRepository tradeParamRepository;

    public CheckTradesExpiryUseCase(IQueryTradesUseCase queryTradesUseCase,
                                    IOfferService offerService,
                                    ITradeRepository tradeRepository,
                                    IOfferRepository offerRepository,
                                    ITradeParamRepository tradeParamRepository) {
        this.queryTradesUseCase = queryTradesUseCase;
        this.offerService = offerService;
        this.tradeRepository = tradeRepository;
        this.offerRepository = offerRepository;
        this.tradeParamRepository = tradeParamRepository;
    }

    @Override
    public void run() {
        TradeQuery query = TradeQuery.builder()
                .addStatus(TradeStatus.UNCONFIRMED)
                .addStatus(TradeStatus.ONGOING)
                .build();
        List<Trade> activeTrades = queryTradesUseCase.apply(query);

        activeTrades.forEach(this::checkTradeExpiration);
    }

    private void checkTradeExpiration(Trade trade) {
        if (LocalDateTime.now().isAfter(
                trade.getLastUpdate().plus(
                        tradeParamRepository.get().getExpirationDays(),
                        ChronoUnit.DAYS
                )
        )) {
            LocalDateTime time = LocalDateTime.now();
            trade.setLastUpdate(time);
            trade.setStatus(TradeStatus.EXPIRED);

            Offer initiatorOffer = offerRepository.getById(trade.getInitiatorOfferID());
            Offer proposedOffer = offerRepository.getById(trade.getProposedOfferID());

            offerService.updateOfferStatus(initiatorOffer, OfferStatus.OPEN, time);
            offerService.updateOfferStatus(proposedOffer, OfferStatus.OPEN, time);

            tradeRepository.save(trade);
        }
    }
}
