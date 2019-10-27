package com.jongber.projectp.object;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

public class GameObject {
    private static int ID = 0;
    private int id;
    private String name;
    private Vector2 transform = new Vector2();
    private HashMap<Class<?>, Object> components = new HashMap<>();

    public Queue<GameObject> collisionQ = new ArrayDeque<>();

    public GameObject(String name) {
        this.name = name;
        GameObject.ID++;
        this.id = GameObject.ID;
    }

    public int getId() {
        return this.id;
    }

    public boolean isName(String name) {
        return this.name.compareTo(name) == 0;
    }

    public String getName() {
        return this.name;
    }

    public Vector2 getTransform() {
        return this.transform;
    }

    public void setTransform(Vector2 transform) {
        this.transform = transform;
    }

    public void addComponent(Class<?> type, Object obj) {
        this.components.put(type, obj);
    }

    public <T> T getComponent(Class<T> type) {
        Object obj = this.components.get(type);
        return type.cast(obj);
    }
}
