package com.natewilliford.fixer.objects;

abstract class Component {

    private GameObject gameObject;

    void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    GameObject getGameObject() {
        return gameObject;
    }

    abstract void onInit();
}
