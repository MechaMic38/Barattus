package com.mechamic38.barattus.core.common;

import com.mechamic38.barattus.core.user.*;
import com.mechamic38.barattus.persistence.user.IUserDataSource;
import com.mechamic38.barattus.persistence.user.LocalUserDataSource;

public class ServiceProvider {

    private static ServiceProvider instance;

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
        userDataSource = new LocalUserDataSource();
        userRepository = new UserRepository(userDataSource, new UserMapper());
        loginService = new LoginService(userRepository);
        registrationService = new RegistrationService(userRepository);

        userRepository.loadFromDataSource();
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
