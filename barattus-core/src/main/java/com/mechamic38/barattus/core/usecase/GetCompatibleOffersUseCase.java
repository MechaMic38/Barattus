package com.mechamic38.barattus.core.usecase;

import com.mechamic38.barattus.core.offer.Offer;
import com.mechamic38.barattus.core.offer.OfferStatus;

import java.util.List;

public class GetCompatibleOffersUseCase implements IGetCompatibleOffersUseCase {

    private final IQueryOffersUseCase queryOffersUseCase;
    private final IGetOfferDataUseCase getOfferDataUseCase;

    public GetCompatibleOffersUseCase(IQueryOffersUseCase queryOffersUseCase, IGetOfferDataUseCase getOfferDataUseCase) {
        this.queryOffersUseCase = queryOffersUseCase;
        this.getOfferDataUseCase = getOfferDataUseCase;
    }

    @Override
    public List<Offer> apply(Offer offer) {
        OfferData offerData = getOfferDataUseCase.apply(offer);

        OfferQuery query = OfferQuery.builder()
                .setCategory(offerData.getCategory())
                .addStatus(OfferStatus.OPEN)
                .build();

        return queryOffersUseCase.apply(query).stream()
                .filter(compatible -> !compatible.equals(offer))
                .toList();
    }
}
