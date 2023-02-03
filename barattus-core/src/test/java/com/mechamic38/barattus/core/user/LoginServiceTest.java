package com.mechamic38.barattus.core.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoginServiceTest {

    IUserRepository userRepository;
    ILoginService loginService;

    @BeforeEach
    public void init() {
        userRepository = new UserRepositoryMock();
        loginService = new LoginService(userRepository);
    }

    @Test
    void should_authenticate_user() {
        userRepository.save(
                new User("Mecha", "666", UserRole.CONFIGURATOR)
        );
        ILoginService.LoginResult result = loginService.loginUser("Mecha", "666");

        Assertions.assertFalse(result.isError());
    }

    @Test
    void should_not_authenticate_user_if_user_not_found() {
        userRepository.save(
                new User("Mecha", "666", UserRole.CONFIGURATOR)
        );
        ILoginService.LoginResult result = loginService.loginUser("Azure", "555");

        Assertions.assertTrue(result.isError());
    }

    @Test
    void should_not_authenticate_user_if_wrong_password() {
        userRepository.save(
                new User("Mecha", "666", UserRole.CONFIGURATOR)
        );
        ILoginService.LoginResult result = loginService.loginUser("Mecha", "555");

        Assertions.assertTrue(result.isError());
    }

    @Test
    void should_accept_config_credentials() {
        ILoginService.LoginResult result = loginService.loginUser("IngSoftware", "Barattus2022");

        Assertions.assertTrue(result.isConfigMatch());
    }
}
