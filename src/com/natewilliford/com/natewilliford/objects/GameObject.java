package com.natewilliford.com.natewilliford.objects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameObject {

    private final Map<Class, Component> components = new HashMap<>();

    public GameObject() {}

    public void addComponent(Component component) {
        component.setGameObject(this);
        components.put(component.getClass(), component);
    }

    public void addComponents(List<Component> components) {
        for (Component c : components) {
            addComponent(c);
        }
    }

    public Component getComponent(Class c) {
        return components.get(c);
    }
}
