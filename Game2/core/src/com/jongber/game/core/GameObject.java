package com.jongber.game.core;

public class GameObject {
    private GameObject parent;
    private PackedArray<GameObject> children = new PackedArray<>();

    public GameObject() {
        this.parent = null;
    }

    public void addChild(GameObject object) {
        object.parent = this;
        this.children.add(object);
    }

    public void removeChild(GameObject object) {
        this.children.remove(object);
    }

    public GameObject[] getChildren() {
        return this.children.getArray();
    }
}
