package com.natewilliford.fixer.objects;

public abstract class Component {

    private GameObject gameObject;

    void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public abstract void onInit();
}
