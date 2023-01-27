package com.mechamic38.barattus.core.offer;

import com.mechamic38.barattus.persistence.offer.OfferLogDTO;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Mapper class for trade logs.
 */
public class OfferLogMapper {

    /**
     * Converts the given OfferLog entity into a OfferLogDTO object.
     *
     * @param offerLog OfferLog entity to map
     * @return OfferLogDTO object
     */
    public OfferLogDTO toDto(OfferLog offerLog) {
        return new OfferLogDTO(
                offerLog.getID().toString(),
                offerLog.time().toString(),
                offerLog.offerID().toString(),
                offerLog.status().name()
        );
    }

    /**
     * Converts the given TradeLogDTO object into a TradeLog entity.
     *
     * @param tradeLogDTO TradeLogDTO object to map
     * @return TradeLog entity
     */
    public OfferLog fromDto(OfferLogDTO tradeLogDTO) {
        return new OfferLog(
                UUID.fromString(tradeLogDTO.getId()),
                LocalDateTime.parse(tradeLogDTO.getTime()),
                UUID.fromString(tradeLogDTO.getOfferID()),
                OfferStatus.valueOf(tradeLogDTO.getStatus())
        );
    }
}
