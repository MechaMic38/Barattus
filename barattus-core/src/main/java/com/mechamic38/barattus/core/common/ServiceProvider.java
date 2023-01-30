package com.mechamic38.barattus.core.common;

import com.mechamic38.barattus.core.category.*;
import com.mechamic38.barattus.core.offer.*;
import com.mechamic38.barattus.core.trade.*;
import com.mechamic38.barattus.core.tradeparams.ITradeParamRepository;
import com.mechamic38.barattus.core.tradeparams.TradeParamRepository;
import com.mechamic38.barattus.core.tradeparams.TradeParamsMapper;
import com.mechamic38.barattus.core.user.*;
import com.mechamic38.barattus.persistence.category.ICategoryDataSource;
import com.mechamic38.barattus.persistence.category.LocalCategoryDataSource;
import com.mechamic38.barattus.persistence.offer.IOfferDataSource;
import com.mechamic38.barattus.persistence.offer.IOfferLogDataSource;
import com.mechamic38.barattus.persistence.offer.LocalOfferDataSource;
import com.mechamic38.barattus.persistence.offer.LocalOfferLogDataSource;
import com.mechamic38.barattus.persistence.trade.ITradeDataSource;
import com.mechamic38.barattus.persistence.trade.LocalTradeDataSource;
import com.mechamic38.barattus.persistence.tradeparams.ITradeParamDataSource;
import com.mechamic38.barattus.persistence.tradeparams.LocalTradeParamDataSource;
import com.mechamic38.barattus.persistence.user.IUserDataSource;
import com.mechamic38.barattus.persistence.user.LocalUserDataSource;

public class ServiceProvider {

    private static ServiceProvider instance;

    private ICategoryService categoryService;
    private ICategoryRepository categoryRepository;
    private ICategoryDataSource categoryDataSource;

    private IOfferService offerService;
    private IOfferRepository offerRepository;
    private IOfferLogRepository offerLogRepository;
    private IOfferDataSource offerDataSource;
    private IOfferLogDataSource offerLogDataSource;

    private ITradeService tradeService;
    private ITradeRepository tradeRepository;
    private ITradeDataSource tradeDataSource;

    private ITradeParamRepository tradeParamRepository;
    private ITradeParamDataSource tradeParamDataSource;

    private IUserService userService;
    private ILoginService loginService;
    private IRegistrationService registrationService;
    private IUserRepository userRepository;
    private IUserDataSource userDataSource;

    private ServiceProvider() {
    }

    public static ServiceProvider getInstance() {
        if (instance == null) {
            synchronized (ServiceProvider.class) {
                if (instance == null) {
                    instance = new ServiceProvider();
                }
            }
        }

        return instance;
    }

    public void buildServices() {
        categoryDataSource = new LocalCategoryDataSource();
        categoryRepository = new CategoryRepository(categoryDataSource, new CategoryMapper());
        categoryService = new CategoryService(categoryRepository);

        offerLogDataSource = new LocalOfferLogDataSource();
        offerLogRepository = new OfferLogRepository(offerLogDataSource, new OfferLogMapper());
        offerDataSource = new LocalOfferDataSource();
        offerRepository = new OfferRepository(offerDataSource, new OfferMapper());
        offerService = new OfferService(offerRepository, offerLogRepository);

        tradeParamDataSource = new LocalTradeParamDataSource();
        tradeParamRepository = new TradeParamRepository(tradeParamDataSource, new TradeParamsMapper());

        userDataSource = new LocalUserDataSource();
        userRepository = new UserRepository(userDataSource, new UserMapper());
        userService = new UserService();
        loginService = new LoginService(userRepository);
        registrationService = new RegistrationService(userRepository);

        tradeDataSource = new LocalTradeDataSource();
        tradeRepository = new TradeRepository(tradeDataSource, new TradeMapper());
        tradeService = new TradeService(tradeRepository, offerService, offerRepository, tradeParamRepository);

        userRepository.loadFromDataSource();
        tradeParamRepository.loadFromDataSource();
        categoryRepository.loadFromDataSource();
        offerRepository.loadFromDataSource();
        offerLogRepository.loadFromDataSource();
        tradeRepository.loadFromDataSource();
    }

    public IOfferService getOfferService() {
        return offerService;
    }

    public IOfferRepository getOfferRepository() {
        return offerRepository;
    }

    public IOfferLogRepository getOfferLogRepository() {
        return offerLogRepository;
    }

    public IOfferDataSource getOfferDataSource() {
        return offerDataSource;
    }

    public IOfferLogDataSource getOfferLogDataSource() {
        return offerLogDataSource;
    }

    public ITradeService getTradeService() {
        return tradeService;
    }

    public ITradeRepository getTradeRepository() {
        return tradeRepository;
    }

    public ITradeDataSource getTradeDataSource() {
        return tradeDataSource;
    }

    public ICategoryService getCategoryService() {
        return categoryService;
    }

    public ICategoryRepository getCategoryRepository() {
        return categoryRepository;
    }

    public ICategoryDataSource getCategoryDataSource() {
        return categoryDataSource;
    }

    public ITradeParamRepository getTradeParamRepository() {
        return tradeParamRepository;
    }

    public ITradeParamDataSource getTradeParamDataSource() {
        return tradeParamDataSource;
    }

    public IUserService getUserService() {
        return userService;
    }

    public ILoginService getLoginService() {
        return loginService;
    }

    public IRegistrationService getRegistrationService() {
        return registrationService;
    }

    public IUserRepository getUserRepository() {
        return userRepository;
    }

    public IUserDataSource getUserDataSource() {
        return userDataSource;
    }
}
