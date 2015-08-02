package com.natewilliford.fixer.objects;

public class User extends GameObject {

    private final String username;
    private final String password;

    public User(int id, String username, String password) {
        super(id);
        this.username = username;
        this.password = password;

        addComponent(new ResourceStorageComponent(Resources.ALL));
        init();
    }

    @Override
    int getType() {
        return GameObjects.Type.USER;
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

    public boolean ownsObject(GameObject object) {
        // A user owns an object either if they have the owner id or if it is the user itself. I.e. the user, which is a
        // GameObject, owns itself.
        return object.getOwnerId() == getId() || object.getId() == getId();
    }
}

