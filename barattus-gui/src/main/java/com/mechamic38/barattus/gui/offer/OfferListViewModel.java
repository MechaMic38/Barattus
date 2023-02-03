package com.mechamic38.barattus.gui.offer;

import com.mechamic38.barattus.core.category.Category;
import com.mechamic38.barattus.core.category.ICategoryService;
import com.mechamic38.barattus.core.offer.Offer;
import com.mechamic38.barattus.core.offer.OfferStatus;
import com.mechamic38.barattus.core.usecase.IQueryOffersUseCase;
import com.mechamic38.barattus.core.usecase.OfferQuery;
import com.mechamic38.barattus.core.user.UserRole;
import com.mechamic38.barattus.gui.common.SessionState;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

public class OfferListViewModel implements IOfferListViewModel {

    private final ICategoryService categoryService;
    private final IQueryOffersUseCase queryOffersUseCase;

    private final BooleanProperty admin = new SimpleBooleanProperty(false);
    private final ListProperty<Category> rootCategories = new SimpleListProperty<>();
    private final ListProperty<Category> leafCategories = new SimpleListProperty<>();
    private final ListProperty<Offer> offers = new SimpleListProperty<>();

    public OfferListViewModel(ICategoryService categoryService, IQueryOffersUseCase queryOffersUseCase) {
        this.categoryService = categoryService;
        this.queryOffersUseCase = queryOffersUseCase;
    }

    @Override
    public void loadSubcategories(Category category) {
        leafCategories.set(FXCollections.observableList(
                categoryService.getLeafCategories(category)
        ));
    }

    @Override
    public void loadOffers(Category category) {
        OfferQuery query;
        if (admin.get()) {
            query = OfferQuery.builder()
                    .setCategory(category)
                    .addStatus(OfferStatus.OPEN)
                    .addStatus(OfferStatus.CLOSED)
                    .addStatus(OfferStatus.IN_EXCHANGE)
                    .build();
        } else {
            query = OfferQuery.builder()
                    .setCategory(category)
                    .addStatus(OfferStatus.OPEN)
                    .build();
        }

        offers.set(FXCollections.observableList(
                queryOffersUseCase.apply(query)
        ));
    }

    @Override
    public void loadOwnOffers(Category category) {
        OfferQuery query;
        if (category == null) {
            query = OfferQuery.builder()
                    .setUser(SessionState.getInstance().getUser())
                    .build();
        } else {
            query = OfferQuery.builder()
                    .setCategory(category)
                    .setUser(SessionState.getInstance().getUser())
                    .build();
        }

        offers.set(FXCollections.observableList(
                queryOffersUseCase.apply(query)
        ));
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
