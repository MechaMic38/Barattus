package com.mechamic38.barattus.gui.offer;

import com.mechamic38.barattus.core.category.Category;
import com.mechamic38.barattus.core.offer.Offer;
import com.mechamic38.barattus.gui.common.ViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;

public interface IOfferListViewModel extends ViewModel {
    void loadSubcategories(Category category);

    void loadOffers(Category category);

    void loadOwnOffers(Category category);

    void setActiveOffer(Offer offer);

    BooleanProperty adminProperty();

    ListProperty<Category> rootCategoriesProperty();

    ListProperty<Category> leafCategoriesProperty();

    ListProperty<Offer> offersProperty();
}
