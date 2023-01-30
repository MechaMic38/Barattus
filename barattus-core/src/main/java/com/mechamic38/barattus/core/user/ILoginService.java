package com.mechamic38.barattus.core.user;

import com.mechamic38.barattus.util.Result;

import java.util.Objects;

public interface ILoginService {

    LoginResult loginUser(String username, String password);

    class LoginResult {
        Result<User> userResult;
        boolean configMatch = false;

        private LoginResult(User user) {
            this.userResult = Result.success(user);
        }

        private LoginResult(String error) {
            this.userResult = Result.error(error);
        }

        private LoginResult(boolean configMatch) {
            this.configMatch = configMatch;
        }

        public static LoginResult success(User user) {
            return new LoginResult(user);
        }

        public static LoginResult error(String error) {
            return new LoginResult(error);
        }

        public static LoginResult configMatch() {
            return new LoginResult(true);
        }

        public boolean isSuccess() {
            return !Objects.isNull(userResult) && userResult.isSuccess();
        }

        public boolean isError() {
            return !Objects.isNull(userResult) && userResult.isError();
        }

        public boolean isConfigMatch() {
            return configMatch;
        }

        public User getUser() {
            return userResult.getResult();
        }

        public String getError() {
            return userResult.getError();
        }
    }
}
