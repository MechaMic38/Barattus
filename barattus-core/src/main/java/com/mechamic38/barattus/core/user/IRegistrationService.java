package com.mechamic38.barattus.core.user;

import com.mechamic38.barattus.util.Result;

public interface IRegistrationService {

    Result<User> registerUser(String username, String password, UserRole userRole);
}
