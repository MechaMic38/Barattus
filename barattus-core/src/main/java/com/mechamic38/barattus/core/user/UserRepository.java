package com.mechamic38.barattus.core.user;

import com.mechamic38.barattus.persistence.user.IUserDataSource;
import com.mechamic38.barattus.persistence.user.UserDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Users repository.
 */
public class UserRepository implements IUserRepository {

    private final List<User> users;
    private final IUserDataSource dataSource;
    private final UserMapper mapper;


    public UserRepository(IUserDataSource dataSource, UserMapper mapper) {
        this.dataSource = dataSource;
        this.mapper = mapper;
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
            dataSource.insert(mapper.toDto(user));
        } else {
            dataSource.update(mapper.toDto(user));
        }
    }

    @Override
    public void loadFromDataSource() {
        for (UserDTO user : dataSource.getAll()) {
            users.add(mapper.fromDto(user));
        }
    }
}
