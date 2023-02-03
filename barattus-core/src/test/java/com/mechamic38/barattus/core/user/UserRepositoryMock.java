package com.mechamic38.barattus.core.user;

import java.util.LinkedList;
import java.util.List;

public class UserRepositoryMock implements IUserRepository {

    private final List<User> users;

    public UserRepositoryMock() {
        this.users = new LinkedList<>();
    }

    @Override
    public User getById(String id) {
        return users.stream()
                .filter(user -> user.checkID(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public void save(User user) {
        if (!this.users.contains(user)) {
            this.users.add(user);
        }
    }

    @Override
    public void loadFromDataSource() {

    }
}
