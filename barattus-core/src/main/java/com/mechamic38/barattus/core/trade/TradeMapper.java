package com.mechamic38.barattus.core.trade;

import com.mechamic38.barattus.persistence.trade.TradeDTO;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class TradeMapper {

    /**
     * Converts the given Trade entity into a TradeDTO object.
     *
     * @param trade Trade entity to map
     * @return TradeDTO object
     */
    public TradeDTO toDto(Trade trade) {
        String place = trade.getTradeDetails().getPlace();
        String day = (trade.getTradeDetails().getDay() != null) ? trade.getTradeDetails().getDay().toString() : null;
        String time = (trade.getTradeDetails().getTime() != null) ? trade.getTradeDetails().getTime().toString() : null;

        return new TradeDTO(
                trade.getID().toString(),
                trade.getLastUpdate().toString(),
                trade.getStatus().name(),
                trade.getEditedCount(),
                trade.getInitiatorUserID(),
                trade.getInitiatorOfferID().toString(),
                trade.getProposedUserID(),
                trade.getProposedOfferID().toString(),
                place,
                day,
                time
        );
    }

    /**
     * Converts the given TradeDTO object into a Trade entity.
     *
     * @param tradeDTO TradeDTO object to map
     * @return Trade entity
     */
    public Trade fromDto(TradeDTO tradeDTO) {
        String place = tradeDTO.getPlace();
        DayOfWeek day = (tradeDTO.getDay() != null) ? DayOfWeek.valueOf(tradeDTO.getDay()) : null;
        LocalTime time = (tradeDTO.getTime() != null) ? LocalTime.parse(tradeDTO.getTime()) : null;

        Trade trade = new Trade(
                UUID.fromString(tradeDTO.getId()),
                tradeDTO.getInitiatorUserID(),
                tradeDTO.getProposedUserID(),
                UUID.fromString(tradeDTO.getInitiatorOfferID()),
                UUID.fromString(tradeDTO.getProposedOfferID())
        );
        trade.setStatus(
                TradeStatus.valueOf(tradeDTO.getStatus())
        );
        trade.setLastUpdate(
                LocalDateTime.parse(tradeDTO.getLastUpdate())
        );
        trade.setEditedCount(
                tradeDTO.getEditedCount()
        );
        trade.setTradeDetails(
                new TradeDetails(place, day, time)
        );

        return trade;
    }
}
