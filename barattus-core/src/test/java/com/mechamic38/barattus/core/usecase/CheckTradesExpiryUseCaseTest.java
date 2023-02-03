package com.mechamic38.barattus.core.usecase;

import com.mechamic38.barattus.core.offer.*;
import com.mechamic38.barattus.core.trade.ITradeRepository;
import com.mechamic38.barattus.core.trade.Trade;
import com.mechamic38.barattus.core.trade.TradeRepositoryMock;
import com.mechamic38.barattus.core.trade.TradeStatus;
import com.mechamic38.barattus.core.tradeparams.HourInterval;
import com.mechamic38.barattus.core.tradeparams.ITradeParamRepository;
import com.mechamic38.barattus.core.tradeparams.TradeParamRepositoryMock;
import com.mechamic38.barattus.core.tradeparams.TradeParams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

public class CheckTradesExpiryUseCaseTest {

    IQueryTradesUseCase queryTradesUseCase;
    IOfferService offerService;
    ITradeRepository tradeRepository;
    IOfferRepository offerRepository;
    IOfferLogRepository offerLogRepository;
    ITradeParamRepository tradeParamRepository;
    ICheckTradesExpiryUseCase checkTradesExpiryUseCase;

    HashMap<String, UUID> tradeIDs;

    @BeforeEach
    public void init() {
        tradeRepository = new TradeRepositoryMock();
        offerRepository = new OfferRepositoryMock();
        offerLogRepository = new OfferLogRepositoryMock();
        tradeParamRepository = new TradeParamRepositoryMock();

        offerService = new OfferService(offerRepository, offerLogRepository);
        queryTradesUseCase = new QueryTradesUseCase(tradeRepository);

        checkTradesExpiryUseCase = new CheckTradesExpiryUseCase(
                queryTradesUseCase,
                offerService,
                tradeRepository,
                offerRepository,
                tradeParamRepository
        );

        tradeParamRepository.importData(getTradeParams());
        tradeIDs = loadData();
    }

    @Test
    void should_check_ongoing_unconfirmed_trade_expiry() {
        checkTradesExpiryUseCase.run();
        Trade trade;
        Offer initiatorOffer;
        Offer proposedOffer;

        // ===============================================
        // Unconfirmed - Expired
        // ===============================================

        trade = tradeRepository.getById(tradeIDs.get("unconfirmed_expired"));
        Assertions.assertEquals(
                TradeStatus.EXPIRED,
                trade.getStatus()
        );
        initiatorOffer = offerRepository.getById(trade.getInitiatorOfferID());
        Assertions.assertEquals(
                OfferStatus.OPEN,
                initiatorOffer.getStatus()
        );
        proposedOffer = offerRepository.getById(trade.getProposedOfferID());
        Assertions.assertEquals(
                OfferStatus.OPEN,
                proposedOffer.getStatus()
        );

        // ===============================================
        // Ongoing - Expired
        // ===============================================

        trade = tradeRepository.getById(tradeIDs.get("ongoing_expired"));
        Assertions.assertEquals(
                TradeStatus.EXPIRED,
                trade.getStatus()
        );
        initiatorOffer = offerRepository.getById(trade.getInitiatorOfferID());
        Assertions.assertEquals(
                OfferStatus.OPEN,
                initiatorOffer.getStatus()
        );
        proposedOffer = offerRepository.getById(trade.getProposedOfferID());
        Assertions.assertEquals(
                OfferStatus.OPEN,
                proposedOffer.getStatus()
        );

        // ===============================================
        // Unconfirmed - Not Expired
        // ===============================================

        trade = tradeRepository.getById(tradeIDs.get("unconfirmed_not_expired"));
        Assertions.assertEquals(
                TradeStatus.UNCONFIRMED,
                trade.getStatus()
        );
        initiatorOffer = offerRepository.getById(trade.getInitiatorOfferID());
        Assertions.assertEquals(
                OfferStatus.COUPLED,
                initiatorOffer.getStatus()
        );
        proposedOffer = offerRepository.getById(trade.getProposedOfferID());
        Assertions.assertEquals(
                OfferStatus.SELECTED,
                proposedOffer.getStatus()
        );

        // ===============================================
        // Ongoing - Not Expired
        // ===============================================

        trade = tradeRepository.getById(tradeIDs.get("ongoing_not_expired"));
        Assertions.assertEquals(
                TradeStatus.ONGOING,
                trade.getStatus()
        );
        initiatorOffer = offerRepository.getById(trade.getInitiatorOfferID());
        Assertions.assertEquals(
                OfferStatus.IN_EXCHANGE,
                initiatorOffer.getStatus()
        );
        proposedOffer = offerRepository.getById(trade.getProposedOfferID());
        Assertions.assertEquals(
                OfferStatus.IN_EXCHANGE,
                proposedOffer.getStatus()
        );

        // ===============================================
        // Accepted - Beyond Expiration
        // ===============================================

        trade = tradeRepository.getById(tradeIDs.get("accepted"));
        Assertions.assertEquals(
                TradeStatus.ACCEPTED,
                trade.getStatus()
        );
        initiatorOffer = offerRepository.getById(trade.getInitiatorOfferID());
        Assertions.assertEquals(
                OfferStatus.CLOSED,
                initiatorOffer.getStatus()
        );
        proposedOffer = offerRepository.getById(trade.getProposedOfferID());
        Assertions.assertEquals(
                OfferStatus.CLOSED,
                proposedOffer.getStatus()
        );

        // ===============================================
        // Rejected - Beyond Expiration
        // ===============================================

        trade = tradeRepository.getById(tradeIDs.get("rejected"));
        Assertions.assertEquals(
                TradeStatus.REJECTED,
                trade.getStatus()
        );
        initiatorOffer = offerRepository.getById(trade.getInitiatorOfferID());
        Assertions.assertEquals(
                OfferStatus.OPEN,
                initiatorOffer.getStatus()
        );
        proposedOffer = offerRepository.getById(trade.getProposedOfferID());
        Assertions.assertEquals(
                OfferStatus.OPEN,
                proposedOffer.getStatus()
        );
    }

    private HashMap<String, UUID> loadData() {
        HashMap<String, UUID> trades = new LinkedHashMap<>();

        // ===============================================
        // Unconfirmed - Expired
        // ===============================================

        Offer initiatorOffer = new Offer(
                UUID.randomUUID(),
                "MechaMic_38",
                "Sport.Ciclismo",
                "MTB Hardtail",
                LocalDateTime.now().minusMonths(1)
        );
        initiatorOffer.setStatus(OfferStatus.COUPLED);
        offerRepository.save(initiatorOffer);

        Offer proposedOffer = new Offer(
                UUID.randomUUID(),
                "AzureTestament",
                "Sport.Ciclismo",
                "MTB Gravel",
                LocalDateTime.now().minusMonths(1)
        );
        proposedOffer.setStatus(OfferStatus.SELECTED);
        offerRepository.save(proposedOffer);

        Trade trade = new Trade(
                UUID.randomUUID(),
                "MechaMic_38",
                "AzureTestament",
                initiatorOffer.getID(),
                proposedOffer.getID()
        );
        trade.setStatus(TradeStatus.UNCONFIRMED);
        trade.setLastUpdate(LocalDateTime.now().minusDays(8));
        tradeRepository.save(trade);
        trades.put("unconfirmed_expired", trade.getID());

        // ===============================================
        // Ongoing - Expired
        // ===============================================

        initiatorOffer = new Offer(
                UUID.randomUUID(),
                "MechaMic_38",
                "Sport.Ciclismo",
                "MTB Hardtail",
                LocalDateTime.now().minusMonths(1)
        );
        initiatorOffer.setStatus(OfferStatus.IN_EXCHANGE);
        offerRepository.save(initiatorOffer);

        proposedOffer = new Offer(
                UUID.randomUUID(),
                "AzureTestament",
                "Sport.Ciclismo",
                "MTB Gravel",
                LocalDateTime.now().minusMonths(1)
        );
        proposedOffer.setStatus(OfferStatus.IN_EXCHANGE);
        offerRepository.save(proposedOffer);

        trade = new Trade(
                UUID.randomUUID(),
                "MechaMic_38",
                "AzureTestament",
                initiatorOffer.getID(),
                proposedOffer.getID()
        );
        trade.setStatus(TradeStatus.ONGOING);
        trade.setLastUpdate(LocalDateTime.now().minusDays(8));
        tradeRepository.save(trade);
        trades.put("ongoing_expired", trade.getID());

        // ===============================================
        // Unconfirmed - Not Expired
        // ===============================================

        initiatorOffer = new Offer(
                UUID.randomUUID(),
                "MechaMic_38",
                "Sport.Ciclismo",
                "MTB Hardtail",
                LocalDateTime.now().minusMonths(1)
        );
        initiatorOffer.setStatus(OfferStatus.COUPLED);
        offerRepository.save(initiatorOffer);

        proposedOffer = new Offer(
                UUID.randomUUID(),
                "AzureTestament",
                "Sport.Ciclismo",
                "MTB Gravel",
                LocalDateTime.now().minusMonths(1)
        );
        proposedOffer.setStatus(OfferStatus.SELECTED);
        offerRepository.save(proposedOffer);

        trade = new Trade(
                UUID.randomUUID(),
                "MechaMic_38",
                "AzureTestament",
                initiatorOffer.getID(),
                proposedOffer.getID()
        );
        trade.setStatus(TradeStatus.UNCONFIRMED);
        trade.setLastUpdate(LocalDateTime.now().minusDays(6));
        tradeRepository.save(trade);
        trades.put("unconfirmed_not_expired", trade.getID());

        // ===============================================
        // Ongoing - Not Expired
        // ===============================================

        initiatorOffer = new Offer(
                UUID.randomUUID(),
                "MechaMic_38",
                "Sport.Ciclismo",
                "MTB Hardtail",
                LocalDateTime.now().minusMonths(1)
        );
        initiatorOffer.setStatus(OfferStatus.IN_EXCHANGE);
        offerRepository.save(initiatorOffer);

        proposedOffer = new Offer(
                UUID.randomUUID(),
                "AzureTestament",
                "Sport.Ciclismo",
                "MTB Gravel",
                LocalDateTime.now().minusMonths(1)
        );
        proposedOffer.setStatus(OfferStatus.IN_EXCHANGE);
        offerRepository.save(proposedOffer);

        trade = new Trade(
                UUID.randomUUID(),
                "MechaMic_38",
                "AzureTestament",
                initiatorOffer.getID(),
                proposedOffer.getID()
        );
        trade.setStatus(TradeStatus.ONGOING);
        trade.setLastUpdate(LocalDateTime.now().minusDays(6));
        tradeRepository.save(trade);
        trades.put("ongoing_not_expired", trade.getID());

        // ===============================================
        // Accepted - Beyond Expiration
        // ===============================================

        initiatorOffer = new Offer(
                UUID.randomUUID(),
                "MechaMic_38",
                "Sport.Ciclismo",
                "MTB Hardtail",
                LocalDateTime.now().minusMonths(1)
        );
        initiatorOffer.setStatus(OfferStatus.CLOSED);
        offerRepository.save(initiatorOffer);

        proposedOffer = new Offer(
                UUID.randomUUID(),
                "AzureTestament",
                "Sport.Ciclismo",
                "MTB Gravel",
                LocalDateTime.now().minusMonths(1)
        );
        proposedOffer.setStatus(OfferStatus.CLOSED);
        offerRepository.save(proposedOffer);

        trade = new Trade(
                UUID.randomUUID(),
                "MechaMic_38",
                "AzureTestament",
                initiatorOffer.getID(),
                proposedOffer.getID()
        );
        trade.setStatus(TradeStatus.ACCEPTED);
        trade.setLastUpdate(LocalDateTime.now().minusDays(8));
        tradeRepository.save(trade);
        trades.put("accepted", trade.getID());

        // ===============================================
        // Rejected - Beyond Expiration
        // ===============================================

        initiatorOffer = new Offer(
                UUID.randomUUID(),
                "MechaMic_38",
                "Sport.Ciclismo",
                "MTB Hardtail",
                LocalDateTime.now().minusMonths(1)
        );
        initiatorOffer.setStatus(OfferStatus.OPEN);
        offerRepository.save(initiatorOffer);

        proposedOffer = new Offer(
                UUID.randomUUID(),
                "AzureTestament",
                "Sport.Ciclismo",
                "MTB Gravel",
                LocalDateTime.now().minusMonths(1)
        );
        proposedOffer.setStatus(OfferStatus.OPEN);
        offerRepository.save(proposedOffer);

        trade = new Trade(
                UUID.randomUUID(),
                "MechaMic_38",
                "AzureTestament",
                initiatorOffer.getID(),
                proposedOffer.getID()
        );
        trade.setStatus(TradeStatus.REJECTED);
        trade.setLastUpdate(LocalDateTime.now().minusDays(8));
        tradeRepository.save(trade);
        trades.put("rejected", trade.getID());

        return trades;
    }

    private TradeParams getTradeParams() {
        TradeParams params = new TradeParams("Brescia", 7);
        params.addPlace("Università");
        params.addDay(DayOfWeek.THURSDAY);
        params.addHourInterval(new HourInterval(
                LocalTime.of(14, 0),
                LocalTime.of(16, 0)
        ));

        return params;
    }
}
