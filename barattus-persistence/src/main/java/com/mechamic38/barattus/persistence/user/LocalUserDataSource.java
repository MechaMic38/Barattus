package com.mechamic38.barattus.persistence.user;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mechamic38.barattus.persistence.common.LocalDataSource;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Gateway that manages the user data into the in-memory hierarchies file.
 */
public class LocalUserDataSource extends LocalDataSource implements IUserDataSource {

    private static final File USER_DATA = new File(
            System.getProperty("barattus.dir.default.path"),
            "user-data.json"
    );
    private static final String KEY = "users";

    /**
     * Creates the local user storage gateway.
     */
    public LocalUserDataSource() {
        super();
        createLocalFile(USER_DATA);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insert(UserDTO userDTO) {
        JsonObject json = load(USER_DATA, KEY);
        JsonArray users = json.getAsJsonArray(KEY);
        //System.out.println("Users during save:");
        //System.out.println(users);

        JsonElement jsonUser = gson.toJsonTree(userDTO);
        users.add(jsonUser);

        return uploadToFile(json, USER_DATA);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(UserDTO userDTO) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserDTO> getAll() {
        JsonObject json = load(USER_DATA, KEY);
        JsonArray usersJson = json.getAsJsonArray(KEY);

        //System.out.println("Users during populate:");
        //System.out.println(usersJson);

        Type usersListType = new TypeToken<List<UserDTO>>() {
        }.getType();

        return gson.fromJson(
                usersJson,
                usersListType
        );
    }
}
