package com.mechamic38.barattus.core.offer;

import com.mechamic38.barattus.persistence.offer.OfferDTO;
import com.mechamic38.barattus.persistence.offer.OfferFieldDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Mapper class for trade offers.
 */
public class OfferMapper {

    /**
     * Converts the given Offer entity into a OfferDTO object.
     *
     * @param offer Offer entity to map
     * @return OfferDTO object
     */
    public OfferDTO toDto(Offer offer) {
        List<OfferFieldDTO> offerFields = offer.getOfferFields().stream()
                .map(this::toDto)
                .toList();

        return new OfferDTO(
                offer.getID().toString(),
                offer.getTitle(),
                offer.getCreationDate().toString(),
                offer.getCategoryID(),
                offer.getUserID().toString(),
                offer.getStatus().name(),
                offerFields
        );
    }

    /**
     * Converts the given OfferDTO object into a Offer entity.
     *
     * @param offerDTO OfferDTO object to map
     * @return Offer entity
     */
    public Offer fromDto(OfferDTO offerDTO) {
        List<OfferField> offerFields = offerDTO.getOfferFields().stream()
                .map(this::fromDto)
                .toList();
        OfferStatus status = OfferStatus.valueOf(offerDTO.getStatus());

        Offer offer = new Offer(
                UUID.fromString(offerDTO.getId()),
                UUID.fromString(offerDTO.getUserID()),
                offerDTO.getCategoryID(),
                offerDTO.getTitle(),
                LocalDateTime.parse(offerDTO.getCreationDate())
        );
        offer.setStatus(status);
        offer.addOfferFields(offerFields);

        return offer;
    }

    /**
     * Converts the given OfferField entity into a OfferFieldDTO object.
     *
     * @param offerField OfferField entity to map
     * @return OfferFieldDTO object
     */
    public OfferFieldDTO toDto(OfferField offerField) {
        return new OfferFieldDTO(
                offerField.getName(),
                offerField.getType().name(),
                offerField.getContent()
        );
    }

    /**
     * Converts the given OfferFieldDTO object into a OfferField entity.
     *
     * @param offerFieldDTO OfferFieldDTO object to map
     * @return OfferField entity
     */
    public OfferField fromDto(OfferFieldDTO offerFieldDTO) {
        return new OfferField(
                offerFieldDTO.getName(),
                OfferField.Type.valueOf(offerFieldDTO.getType()),
                offerFieldDTO.getContent()
        );
    }
}
