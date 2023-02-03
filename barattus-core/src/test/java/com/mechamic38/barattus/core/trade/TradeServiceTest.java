package com.mechamic38.barattus.core.trade;

import com.mechamic38.barattus.core.category.Category;
import com.mechamic38.barattus.core.offer.*;
import com.mechamic38.barattus.core.tradeparams.HourInterval;
import com.mechamic38.barattus.core.tradeparams.ITradeParamRepository;
import com.mechamic38.barattus.core.tradeparams.TradeParamRepositoryMock;
import com.mechamic38.barattus.core.tradeparams.TradeParams;
import com.mechamic38.barattus.core.user.User;
import com.mechamic38.barattus.core.user.UserRole;
import com.mechamic38.barattus.util.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.LinkedList;

public class TradeServiceTest {

    ITradeRepository tradeRepository;
    ITradeService tradeService;

    ITradeParamRepository tradeParamRepository;

    IOfferLogRepository offerLogRepository;
    IOfferRepository offerRepository;
    IOfferService offerService;

    @BeforeEach
    public void init() {
        offerRepository = new OfferRepositoryMock();
        offerLogRepository = new OfferLogRepositoryMock();
        offerService = new OfferService(offerRepository, offerLogRepository);

        tradeParamRepository = new TradeParamRepositoryMock();

        tradeRepository = new TradeRepositoryMock();
        tradeService = new TradeService(tradeRepository, offerService, offerRepository, tradeParamRepository);

        tradeParamRepository.importData(getTradeParams());
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

    @Test
    void should_create_trade() {
        User initiator = new User("MechaMic_38", "666", UserRole.END_USER);
        Category initiatorCategory = new Category("Sport", "Bici", "Sport", "Biciclette");
        Offer initiatorOffer = offerService.createOffer(initiator, initiatorCategory, "MTB", new LinkedList<>()).getResult();

        User proposed = new User("AzureTestament", "Rance", UserRole.END_USER);
        Category proposedCategory = new Category("Sport", "Bici", "Sport", "Biciclette");
        Offer proposedOffer = offerService.createOffer(proposed, proposedCategory, "Gravel", new LinkedList<>()).getResult();

        Result<Trade> result = tradeService.createTrade(initiatorOffer, proposedOffer);

        Assertions.assertFalse(result.isError());

        Trade trade = result.getResult();
        Assertions.assertEquals(TradeStatus.UNCONFIRMED, trade.getStatus());

        Assertions.assertEquals(OfferStatus.COUPLED, initiatorOffer.getStatus());
        Assertions.assertEquals(OfferStatus.SELECTED, proposedOffer.getStatus());
    }

    @Test
    void trade_users_must_be_different() {
        User user = new User("MechaMic_38", "666", UserRole.END_USER);
        Category initiatorCategory = new Category("Sport", "Bici", "Sport", "Biciclette");
        Offer initiatorOffer = offerService.createOffer(user, initiatorCategory, "MTB", new LinkedList<>()).getResult();

        Category proposedCategory = new Category("Sport", "Bici", "Sport", "Biciclette");
        Offer proposedOffer = offerService.createOffer(user, proposedCategory, "Gravel", new LinkedList<>()).getResult();

        Result<Trade> result = tradeService.createTrade(initiatorOffer, proposedOffer);

        Assertions.assertTrue(result.isError());
    }

    @Test
    void offer_categories_must_be_compatible() {
        User initiator = new User("MechaMic_38", "666", UserRole.END_USER);
        Category initiatorCategory = new Category("Sport", "Bici", "Sport", "Biciclette");
        Offer initiatorOffer = offerService.createOffer(initiator, initiatorCategory, "MTB", new LinkedList<>()).getResult();

        User proposed = new User("AzureTestament", "Rance", UserRole.END_USER);
        Category proposedCategory = new Category("Sport", "Accessori", "Sport", "Biciclette");
        Offer proposedOffer = offerService.createOffer(proposed, proposedCategory, "Gravel", new LinkedList<>()).getResult();

        Result<Trade> result = tradeService.createTrade(initiatorOffer, proposedOffer);

        Assertions.assertTrue(result.isError());
    }

    @Test
    void should_reject_trade() {
        User initiator = new User("MechaMic_38", "666", UserRole.END_USER);
        Category initiatorCategory = new Category("Sport", "Bici", "Sport", "Biciclette");
        Offer initiatorOffer = offerService.createOffer(initiator, initiatorCategory, "MTB", new LinkedList<>()).getResult();

        User proposed = new User("AzureTestament", "Rance", UserRole.END_USER);
        Category proposedCategory = new Category("Sport", "Bici", "Sport", "Biciclette");
        Offer proposedOffer = offerService.createOffer(proposed, proposedCategory, "Gravel", new LinkedList<>()).getResult();

        Result<Trade> result = tradeService.createTrade(initiatorOffer, proposedOffer);

        Trade trade = result.getResult();
        result = tradeService.rejectTrade(trade, proposed);

        Assertions.assertFalse(result.isError());

        trade = result.getResult();
        Assertions.assertEquals(TradeStatus.REJECTED, trade.getStatus());

        Assertions.assertEquals(OfferStatus.OPEN, initiatorOffer.getStatus());
        Assertions.assertEquals(OfferStatus.OPEN, proposedOffer.getStatus());
    }

    @Test
    void should_confirm_trade() {
        User initiator = new User("MechaMic_38", "666", UserRole.END_USER);
        Category initiatorCategory = new Category("Sport", "Bici", "Sport", "Biciclette");
        Offer initiatorOffer = offerService.createOffer(initiator, initiatorCategory, "MTB", new LinkedList<>()).getResult();

        User proposed = new User("AzureTestament", "Rance", UserRole.END_USER);
        Category proposedCategory = new Category("Sport", "Bici", "Sport", "Biciclette");
        Offer proposedOffer = offerService.createOffer(proposed, proposedCategory, "Gravel", new LinkedList<>()).getResult();

        Result<Trade> result = tradeService.createTrade(initiatorOffer, proposedOffer);

        Trade trade = result.getResult();
        TradeDetails details = new TradeDetails(
                "Università",
                DayOfWeek.THURSDAY,
                LocalTime.of(15, 0)
        );
        result = tradeService.confirmTrade(trade, details, proposed);

        Assertions.assertFalse(result.isError());

        trade = result.getResult();
        Assertions.assertEquals(TradeStatus.ONGOING, trade.getStatus());

        Assertions.assertEquals(OfferStatus.IN_EXCHANGE, initiatorOffer.getStatus());
        Assertions.assertEquals(OfferStatus.IN_EXCHANGE, proposedOffer.getStatus());
    }

    @Test
    void should_edit_trade_details() {
        User initiator = new User("MechaMic_38", "666", UserRole.END_USER);
        Category initiatorCategory = new Category("Sport", "Bici", "Sport", "Biciclette");
        Offer initiatorOffer = offerService.createOffer(initiator, initiatorCategory, "MTB", new LinkedList<>()).getResult();

        User proposed = new User("AzureTestament", "Rance", UserRole.END_USER);
        Category proposedCategory = new Category("Sport", "Bici", "Sport", "Biciclette");
        Offer proposedOffer = offerService.createOffer(proposed, proposedCategory, "Gravel", new LinkedList<>()).getResult();

        Result<Trade> result = tradeService.createTrade(initiatorOffer, proposedOffer);

        Trade trade = result.getResult();
        TradeDetails details = new TradeDetails(
                "Università",
                DayOfWeek.THURSDAY,
                LocalTime.of(15, 0)
        );
        result = tradeService.confirmTrade(trade, details, proposed);

        trade = result.getResult();
        result = tradeService.editTradeDetails(trade, details, initiator);

        Assertions.assertFalse(result.isError());

        trade = result.getResult();
        Assertions.assertEquals(TradeStatus.ONGOING, trade.getStatus());

        Assertions.assertEquals(OfferStatus.IN_EXCHANGE, initiatorOffer.getStatus());
        Assertions.assertEquals(OfferStatus.IN_EXCHANGE, proposedOffer.getStatus());
    }

    @Test
    void should_accept_trade() {
        User initiator = new User("MechaMic_38", "666", UserRole.END_USER);
        Category initiatorCategory = new Category("Sport", "Bici", "Sport", "Biciclette");
        Offer initiatorOffer = offerService.createOffer(initiator, initiatorCategory, "MTB", new LinkedList<>()).getResult();

        User proposed = new User("AzureTestament", "Rance", UserRole.END_USER);
        Category proposedCategory = new Category("Sport", "Bici", "Sport", "Biciclette");
        Offer proposedOffer = offerService.createOffer(proposed, proposedCategory, "Gravel", new LinkedList<>()).getResult();

        Result<Trade> result = tradeService.createTrade(initiatorOffer, proposedOffer);

        Trade trade = result.getResult();
        result = tradeService.acceptTrade(trade, proposed);

        Assertions.assertFalse(result.isError());

        trade = result.getResult();
        Assertions.assertEquals(TradeStatus.ACCEPTED, trade.getStatus());

        Assertions.assertEquals(OfferStatus.CLOSED, initiatorOffer.getStatus());
        Assertions.assertEquals(OfferStatus.CLOSED, proposedOffer.getStatus());
    }
}
