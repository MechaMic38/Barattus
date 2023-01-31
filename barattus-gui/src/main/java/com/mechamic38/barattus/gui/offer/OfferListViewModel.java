package com.mechamic38.barattus.gui.offer;

import com.mechamic38.barattus.core.category.Category;
import com.mechamic38.barattus.core.category.ICategoryService;
import com.mechamic38.barattus.core.offer.IOfferService;
import com.mechamic38.barattus.core.offer.Offer;
import com.mechamic38.barattus.core.user.UserRole;
import com.mechamic38.barattus.gui.common.SessionState;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

public class OfferListViewModel implements IOfferListViewModel {

    private final ICategoryService categoryService;
    private final IOfferService offerService;

    private final BooleanProperty admin = new SimpleBooleanProperty(false);
    private final ListProperty<Category> rootCategories = new SimpleListProperty<>();
    private final ListProperty<Category> leafCategories = new SimpleListProperty<>();
    private final ListProperty<Offer> offers = new SimpleListProperty<>();

    public OfferListViewModel(ICategoryService categoryService, IOfferService offerService) {
        this.categoryService = categoryService;
        this.offerService = offerService;
    }

    @Override
    public void loadSubcategories(Category category) {
        leafCategories.set(FXCollections.observableList(
                categoryService.getLeafCategories(category)
        ));
    }

    @Override
    public void loadOffers(Category category) {
        if (admin.get()) {
            offers.set(FXCollections.observableList(
                    offerService.getOpenOffersByCategory(category)
            ));
        } else {
            offers.set(FXCollections.observableList(
                    offerService.getOpenClosedInExchangeOffersByCategory(category)
            ));
        }
    }

    @Override
    public void loadOwnOffers(Category category) {
        if (category == null) {
            offers.set(FXCollections.observableList(
                    offerService.getOffersByUser(
                            SessionState.getInstance().getUser()
                    )
            ));
        } else {
            offers.set(FXCollections.observableList(
                    offerService.getOffersByCategoryAndUser(
                            category,
                            SessionState.getInstance().getUser()
                    )
            ));
        }
    }

    @Override
    public void setActiveOffer(Offer offer) {
        SessionState.getInstance().setOffer(offer);
    }

    @Override
    public void initialize() {
        admin.set(
                SessionState.getInstance().getUser().getRole().equals(UserRole.CONFIGURATOR)
        );
        rootCategories.set(FXCollections.observableList(
                categoryService.getHierarchies()
        ));
    }

    @Override
    public BooleanProperty adminProperty() {
        return admin;
    }

    @Override
    public ListProperty<Category> rootCategoriesProperty() {
        return rootCategories;
    }

    @Override
    public ListProperty<Category> leafCategoriesProperty() {
        return leafCategories;
    }

    @Override
    public ListProperty<Offer> offersProperty() {
        return offers;
    }
}
