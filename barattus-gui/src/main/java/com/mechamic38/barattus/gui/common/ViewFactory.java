package com.mechamic38.barattus.gui.common;

import com.mechamic38.barattus.core.common.ServiceProvider;
import com.mechamic38.barattus.gui.category.*;
import com.mechamic38.barattus.gui.login.*;
import com.mechamic38.barattus.gui.main.IMainViewModel;
import com.mechamic38.barattus.gui.main.MainView;
import com.mechamic38.barattus.gui.main.MainViewModel;
import com.mechamic38.barattus.gui.offer.*;
import com.mechamic38.barattus.gui.trade.*;
import com.mechamic38.barattus.gui.tradeparams.ITradeParamsViewModel;
import com.mechamic38.barattus.gui.tradeparams.TradeParamsView;
import com.mechamic38.barattus.gui.tradeparams.TradeParamsViewModel;

public class ViewFactory {

    public static View createView(Views view) {
        return switch (view) {
            case CATEGORY_EDITOR -> createCategoryEditorView();
            case CATEGORY_LIST -> createCategoryListView();
            case CREATE_OFFER -> createCreateOfferView();
            case FIELD_EDITOR -> createFieldEditorView();
            case MAIN -> createMainView();
            case LOGIN -> createLoginView();
            case OFFER_DETAILS -> createOfferDetailsView();
            case OFFER_LIST -> createOfferListView();
            case REGISTER -> createRegistration();
            case SELECT_TRADE_OFFER -> createSelectTradeOfferView();
            case TRADE_EDITOR -> createTradeEditorView();
            case TRADE_LIST -> createTradeListView();
            case TRADE_PARAMS -> createTradeParamsView();
            default -> throw new IllegalArgumentException("Invalid view type");
        };
    }

    private static View createCategoryEditorView() {
        ICategoryEditorVIewModel viewModel = new CategoryEditorViewModel(
                ServiceProvider.getInstance().getCategoryService(),
                ServiceProvider.getInstance().getCategoryRepository()
        );
        return new CategoryEditorView(viewModel);
    }

    private static View createCategoryListView() {
        ICategoryListViewModel viewModel = new CategoryListViewModel(
                ServiceProvider.getInstance().getCategoryService(),
                ServiceProvider.getInstance().getCategoryRepository()
        );
        return new CategoryListView(viewModel);
    }

    private static View createCreateOfferView() {
        ICreateOfferViewModel viewModel = new CreateOfferViewModel(
                ServiceProvider.getInstance().getCategoryService(),
                ServiceProvider.getInstance().getOfferRepository()
        );
        return new CreateOfferView(viewModel);
    }

    private static View createFieldEditorView() {
        IFieldEditorViewModel viewModel = new FieldEditorViewModel(
                ServiceProvider.getInstance().getCategoryService()
        );
        return new FieldEditorView(viewModel);
    }


    private static View createMainView() {
        IMainViewModel viewModel = new MainViewModel(
                ServiceProvider.getInstance().getUserService()
        );
        return new MainView(viewModel);
    }

    private static View createLoginView() {
        ILoginViewModel viewModel = new LoginViewModel(
                ServiceProvider.getInstance().getLoginService()
        );
        return new LoginView(viewModel);
    }

    private static View createOfferDetailsView() {
        IOfferDetailsViewModel viewModel = new OfferDetailsViewModel(
                ServiceProvider.getInstance().getOfferService(),
                ServiceProvider.getInstance().getOfferRepository()
        );
        return new OfferDetailsView(viewModel);
    }

    private static View createOfferListView() {
        IOfferListViewModel viewModel = new OfferListViewModel(
                ServiceProvider.getInstance().getCategoryService(),
                ServiceProvider.getInstance().getOfferService()
        );
        return new OfferListView(viewModel);
    }

    private static View createRegistration() {
        IRegistrationViewModel viewModel = new RegistrationViewModel(
                ServiceProvider.getInstance().getRegistrationService()
        );
        return new RegistrationView(viewModel);
    }

    private static View createSelectTradeOfferView() {
        ISelectTradeOfferViewModel viewModel = new SelectTradeOfferViewModel(
                ServiceProvider.getInstance().getTradeService(),
                ServiceProvider.getInstance().getOfferService(),
                ServiceProvider.getInstance().getOfferRepository()
        );
        return new SelectTradeOfferView(viewModel);
    }

    private static View createTradeEditorView() {
        ITradeEditorViewModel viewModel = new TradeEditorViewModel(
                ServiceProvider.getInstance().getTradeService(),
                ServiceProvider.getInstance().getOfferService(),
                ServiceProvider.getInstance().getOfferRepository(),
                ServiceProvider.getInstance().getTradeParamRepository()
        );
        return new TradeEditorView(viewModel);
    }

    private static View createTradeListView() {
        ITradeListViewModel viewModel = new TradeListViewModel(
                ServiceProvider.getInstance().getTradeService()
        );
        return new TradeListView(viewModel);
    }

    private static View createTradeParamsView() {
        ITradeParamsViewModel viewModel = new TradeParamsViewModel(
                ServiceProvider.getInstance().getTradeParamsService(),
                ServiceProvider.getInstance().getTradeParamRepository()
        );
        return new TradeParamsView(viewModel);
    }
}
