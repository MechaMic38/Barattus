package com.mechamic38.barattus.core.user.validation;

import com.mechamic38.barattus.core.user.IUserRepository;
import com.mechamic38.barattus.core.user.User;

import java.util.Objects;

public class UserExistsLink extends LoginChainLink {

    private final IUserRepository userRepository;

    public UserExistsLink(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String check(String username, String password) {
        User user = userRepository.getById(username);

        if (Objects.isNull(user)) {
            return "login.user.not_found";
        }
        if (!user.verifyPassword(password)) {
            return "login.user.credentials.wrong";
        }
        return checkNext(username, password);
    }
}
