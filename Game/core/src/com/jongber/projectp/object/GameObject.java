package com.jongber.projectp.object;

import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;

public class GameObject {

    private String name;
    private Vector2 transform = new Vector2();
    private HashMap<Class<?>, Object> components = new HashMap<>();

    public GameObject(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Vector2 getTransform() {
        return this.transform;
    }

    public void addComponent(Class<?> type, Object obj) {
        this.components.put(type, obj);
    }

    public <T> T getComponent(Class<T> type) {
        Object obj = this.components.get(type);
        return type.cast(obj);
    }
}
