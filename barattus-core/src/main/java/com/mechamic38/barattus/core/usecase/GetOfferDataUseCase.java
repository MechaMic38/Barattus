package com.mechamic38.barattus.core.usecase;

import com.mechamic38.barattus.core.category.Category;
import com.mechamic38.barattus.core.category.ICategoryRepository;
import com.mechamic38.barattus.core.offer.Offer;

/**
 * Returns all the offer data, including the category.
 */
public class GetOfferDataUseCase implements IGetOfferDataUseCase {

    private final ICategoryRepository categoryRepository;

    public GetOfferDataUseCase(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public OfferData apply(Offer offer) {
        Category offerCategory = categoryRepository.getById(offer.getCategoryID());
        return OfferData.builder(offer)
                .setCategory(offerCategory)
                .build();
    }
}
