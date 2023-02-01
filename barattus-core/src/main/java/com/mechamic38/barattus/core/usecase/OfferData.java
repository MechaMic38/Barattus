package com.mechamic38.barattus.core.usecase;

import com.mechamic38.barattus.core.category.Category;
import com.mechamic38.barattus.core.offer.Offer;

/**
 * Holder class for offer data.
 */
public class OfferData {

    private final Offer offer;
    private final Category category;

    private OfferData(Offer offer, Category category) {
        this.offer = offer;
        this.category = category;
    }

    public static OfferDataBuilder builder(Offer offer) {
        return new OfferDataBuilder(offer);
    }

    public Offer getOffer() {
        return offer;
    }

    public Category getCategory() {
        return category;
    }


    /**
     * {@link OfferData} builder.
     */
    static class OfferDataBuilder {
        private Offer offer;
        private Category category;

        public OfferDataBuilder(Offer offer) {
            this.offer = offer;
        }

        public OfferDataBuilder setOffer(Offer offer) {
            this.offer = offer;
            return this;
        }

        public OfferDataBuilder setCategory(Category category) {
            this.category = category;
            return this;
        }

        public OfferData build() {
            return new OfferData(offer, category);
        }
    }
}
