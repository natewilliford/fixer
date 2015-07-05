package com.natewilliford.fixer.objects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameObject {

    private final Map<Class, Component> components = new HashMap<>();
    private final Map<Class, Updatable> updatableComponents = new HashMap<>();

    public GameObject() {}

    public void addComponent(Component component) {
        component.setGameObject(this);
        components.put(component.getClass(), component);
        if (component instanceof Updatable) {
            updatableComponents.put(component.getClass(), (Updatable) component);
        }
    }

    public void addComponents(List<Component> components) {
        for (Component c : components) {
            addComponent(c);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> T getComponent(Class<T> c) {
        // TODO: Make sure we've called init so all the components get a chance to start.
        // TODO: Throw something if it doesn't exist.
        return (T) components.get(c);
    }

    public void init() {
        // TODO: Make sure it's not initialized more than once.
        for (Component c : components.values()) {
            c.onInit();
        }
    }

    public void update(long elapsed) {
        // TODO: Make sure we've called init so all the components get a chance to start.
        for (Updatable c : updatableComponents.values()) {
            c.onUpdate(elapsed);
        }
    }
}
