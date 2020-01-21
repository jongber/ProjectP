package com.jongber.game.core;

import com.jongber.game.core.component.TransformComponent;
import com.jongber.game.core.util.PackedArray;

import java.util.HashMap;
import java.util.Map;

public class GameObject {

    public enum State {
        Active,
        InActive,
        Dead,
    }

    private GameObject parent;
    private PackedArray children = new PackedArray();
    private Map<Class, Component> componentMap = new HashMap<>();

    public String name = "";
    public State state = State.Active;
    public TransformComponent transform = new TransformComponent();

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

    public void addComponent(Component component) {
        this.componentMap.put(component.getClass(), component);
    }

    public <E> E getComponent(Class componentType) {

        Component obj = this.componentMap.get(componentType);
        if (obj != null && componentType.isInstance(obj)) {
            return (E)this.componentMap.get(componentType);
        }

        return null;
    }

    public GameObject getParent() {
        return this.parent;
    }

    public Object[] getChildren() {
        return this.children.getArray();
    }
}
