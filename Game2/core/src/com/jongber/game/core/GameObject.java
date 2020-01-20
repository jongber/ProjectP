package com.jongber.game.core;

import java.util.HashMap;
import java.util.Map;

public class GameObject {

    public interface Callback {
        void modified(GameObject object);
    }

    public enum State {
        Active,
        InActive,
        Dead,
    }

    private GameObject parent;
    private PackedArray<GameObject> children = new PackedArray<>();
    private Callback callback;
    private Map<Class, Component> componentMap = new HashMap<>();

    public State state = State.Active;

    public GameObject(Callback callback) {
        this.parent = null;
        this.callback = callback;
    }

    public void addChild(GameObject object) {
        object.parent = this;
        this.children.add(object);

        if (this.callback != null)
            this.callback.modified(this);
    }

    public void removeChild(GameObject object) {
        this.children.remove(object);

        if (this.callback != null)
            this.callback.modified(this);
    }

    public void addComponent(Component component) {
        this.componentMap.put(component.getClass(), component);
    }

    public Component getComponent(Class componentType) {
        return this.componentMap.get(componentType);
    }

    public GameObject[] getChildren() {
        return this.children.getArray();
    }
}
