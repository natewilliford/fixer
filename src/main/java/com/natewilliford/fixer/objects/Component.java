package com.natewilliford.fixer.objects;

import com.sun.istack.internal.Nullable;

public abstract class Component {

    @Nullable
    private GameObject gameObject;

    @Nullable
    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public GameObject getGameObject() {
        return gameObject;
    }
}
