package com.mechamic38.barattus.core.user;

import com.mechamic38.barattus.core.user.validation.ConfigCredentialsLink;
import com.mechamic38.barattus.core.user.validation.LoginChainLink;
import com.mechamic38.barattus.core.user.validation.UserExistsLink;

public class LoginService implements ILoginService {

    private final IUserRepository userRepository;

    public LoginService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public LoginResult loginUser(String username, String password) {
        String validation = LoginChainLink.link(
                new ConfigCredentialsLink(),
                new UserExistsLink(userRepository)
        ).check(username, password);

        if ("configMatch".equals(validation)) {
            return LoginResult.configMatch();
        }
        if (!validation.isBlank()) {
            return LoginResult.error(validation);
        }

        User user = userRepository.getById(username);
        return LoginResult.success(user);
    }
}
