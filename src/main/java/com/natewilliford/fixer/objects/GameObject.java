package com.natewilliford.fixer.objects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GameObject {

    private final Map<Class, Component> components = new HashMap<>();
    private final Map<Class, Updatable> updatableComponents = new HashMap<>();

    private final long id;
    private long ownerId = -1;

    GameObject(long objectId) {
        this.id = objectId;
    }

    abstract int getType();

    long getId() {
        return id;
    }

    void setOwner(User user) {
        ownerId = user.getId();
    }

    long getOwnerId() {
        return ownerId;
    }

    void addComponent(Component component) {
        component.setGameObject(this);
        components.put(component.getClass(), component);
        if (component instanceof Updatable) {
            updatableComponents.put(component.getClass(), (Updatable) component);
        }
    }

    void addComponents(List<Component> components) {
        for (Component c : components) {
            addComponent(c);
        }
    }

    @SuppressWarnings("unchecked")
    <T extends Component> T getComponent(Class<T> c) {
        // TODO: Make sure we've called init so all the components get a chance to start.
        // TODO: Throw something if it doesn't exist?
        return (T) components.get(c);
    }

    void init() {
        // TODO: Make sure it's initialized exactly once.
        for (Component c : components.values()) {
            c.onInit();
        }
    }

    void update(long elapsed) {
        // TODO: Make sure we've called init so all the components get a chance to start.
        for (Updatable c : updatableComponents.values()) {
            c.onUpdate(elapsed);
        }
    }
}
