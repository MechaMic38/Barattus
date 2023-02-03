package com.mechamic38.barattus.core.common;

import com.mechamic38.barattus.core.usecase.*;

public class UseCaseProvider {

    public static IGetTradeDataUseCase getTradeDataUseCase() {
        return new GetTradeDataUseCase(
                getOfferDataUseCase(),
                ServiceProvider.getInstance().getOfferRepository()
        );
    }

    public static IGetOfferDataUseCase getOfferDataUseCase() {
        return new GetOfferDataUseCase(
                ServiceProvider.getInstance().getCategoryRepository()
        );
    }

    public static ICheckTradesExpiryUseCase checkTradesExpiryUseCase() {
        return new CheckTradesExpiryUseCase(
                queryTradesUseCase(),
                ServiceProvider.getInstance().getOfferService(),
                ServiceProvider.getInstance().getTradeRepository(),
                ServiceProvider.getInstance().getOfferRepository(),
                ServiceProvider.getInstance().getTradeParamRepository()
        );
    }

    public static IQueryTradesUseCase queryTradesUseCase() {
        return new QueryTradesUseCase(
                ServiceProvider.getInstance().getTradeRepository()
        );
    }

    public static IQueryOffersUseCase queryOffersUseCase() {
        return new QueryOffersUseCase(
                ServiceProvider.getInstance().getOfferRepository()
        );
    }

    public static IGetCompatibleOffersUseCase getCompatibleOffersUseCase() {
        return new GetCompatibleOffersUseCase(
                queryOffersUseCase(),
                getOfferDataUseCase()
        );
    }
}
