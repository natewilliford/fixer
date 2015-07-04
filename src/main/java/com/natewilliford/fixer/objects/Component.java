package com.natewilliford.fixer.objects;

public abstract class Component {

    private GameObject gameObject;

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public GameObject getGameObject() {
        return gameObject;
    }
}
