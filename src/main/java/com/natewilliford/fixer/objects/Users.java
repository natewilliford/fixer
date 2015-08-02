package com.natewilliford.fixer.objects;

import java.util.HashMap;
import java.util.Map;

public class Users {

    public static final long SYSTEM_USER_ID = -1;

    private Map<String, User> usersByName = new HashMap<>();
    private Map<Integer, User> usersById = new HashMap<>();

    void addUser(User user) {
        usersByName.put(user.getUsername(), user);
        usersById.put(user.getId(), user);
    }

    User getForId(long id) {
        return usersById.get(id);
    }

    public User getForName(String username) {
        return usersByName.get(username);
    }
}
