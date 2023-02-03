package com.mechamic38.barattus.core.user;

import com.mechamic38.barattus.util.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {

    IUserRepository userRepository;
    IRegistrationService registrationService;

    @BeforeEach
    public void init() {
        userRepository = new UserRepositoryMock();
        registrationService = new RegistrationService(userRepository);
    }

    @Test
    void should_register_user() {
        Result<User> result = registrationService.registerUser("Mecha", "666", UserRole.CONFIGURATOR);

        Assertions.assertFalse(result.isError());

        User user = result.getResult();
        Assertions.assertNotNull(userRepository.getById(user.getID()));
    }

    @Test
    void should_not_register_if_username_exists() {
        userRepository.save(
                new User("Mecha", "666", UserRole.CONFIGURATOR)
        );
        Result<User> result = registrationService.registerUser("Mecha", "666", UserRole.CONFIGURATOR);

        Assertions.assertTrue(result.isError());
    }
}
