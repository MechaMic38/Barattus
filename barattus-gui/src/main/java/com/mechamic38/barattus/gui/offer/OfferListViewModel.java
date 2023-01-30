package com.mechamic38.barattus.gui.offer;

import com.mechamic38.barattus.core.category.ICategoryService;
import com.mechamic38.barattus.core.offer.IOfferService;

public class OfferListViewModel implements IOfferListViewModel {

    private final ICategoryService categoryService;
    private final IOfferService offerService;

    public OfferListViewModel(ICategoryService categoryService, IOfferService offerService) {
        this.categoryService = categoryService;
        this.offerService = offerService;
    }
}
