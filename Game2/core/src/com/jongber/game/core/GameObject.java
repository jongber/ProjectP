package com.jongber.game.core;

import com.jongber.game.core.component.Component;
import com.jongber.game.core.component.TransformComponent;
import com.jongber.game.core.util.PackedArray;

import java.util.HashMap;
import java.util.Map;

public class GameObject {

    private GameObject parent;
    private final PackedArray<GameObject> children = new PackedArray<>();
    private final Map<Class, Component> componentMap = new HashMap<>();

    public String name = "";
    public final TransformComponent transform = new TransformComponent();

    public GameObject(String name) {
        this();
        this.name = name;
    }

    public GameObject() {
        this.parent = null;
    }

    public void addComponent(Component component) {
        this.componentMap.put(component.getClass(), component);
    }

    void addChild(GameObject object) {
        object.parent = this;
        this.children.add(object);
    }

    boolean removeChild(GameObject object) {
        return this.children.remove(object);
    }

    public boolean hasComponent(Class type) {
        return this.componentMap.containsKey(type);
    }

    public <E> E getComponent(Class<E> componentType) {

        Component obj = this.componentMap.get(componentType);
        if (componentType.isInstance(obj)) {
            return componentType.cast(obj);
        }

        return null;
    }

    public GameObject getParent() {
        return this.parent;
    }

    public GameObject[] getChildren() {
        return this.children.toArray(GameObject.class);
    }

    public int getChildrenSize() {
        return this.children.size();
    }

    public GameObject getChild(int index) {
        return this.children.get(index);
    }
}
