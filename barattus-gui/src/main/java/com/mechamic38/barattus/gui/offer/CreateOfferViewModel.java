package com.mechamic38.barattus.gui.offer;

import com.mechamic38.barattus.core.category.ICategoryService;
import com.mechamic38.barattus.core.offer.IOfferRepository;

public class CreateOfferViewModel implements ICreateOfferViewModel {

    private final ICategoryService categoryService;
    private final IOfferRepository offerRepository;

    public CreateOfferViewModel(ICategoryService categoryService, IOfferRepository offerRepository) {
        this.categoryService = categoryService;
        this.offerRepository = offerRepository;
    }
}
