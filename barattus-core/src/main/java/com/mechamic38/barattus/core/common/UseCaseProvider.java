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

    public static IGetTradesByStatusUseCase getTradesByStatusUseCase() {
        return new GetTradeByStatusUseCase(
                ServiceProvider.getInstance().getTradeRepository()
        );
    }
}
