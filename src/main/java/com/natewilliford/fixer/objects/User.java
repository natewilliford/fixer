package com.natewilliford.fixer.objects;

public class User extends GameObject {

    private final long id;
    private final String username;
    private final String password;

    public User(long id, String username, String password) {
        super(id);
        this.id = id;
        this.username = username;
        this.password = password;

        addComponent(new ResourceStorageComponent(Resources.ALL));
        init();
    }

    @Override
    int getType() {
        return GameObjects.Type.USER;
    }

    @Override
    long getOwnerId() {
        System.out.println("WARNING: Getting a user's ownerId which doesn't make sense.");
        return Users.SYSTEM_USER_ID;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    // TODO: I don't like this one bit.
    public String getPassword() {
        return password;
    }

    public boolean validPassword(String checkPassword) {
        return password.equals(checkPassword);
    }
}

