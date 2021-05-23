package com.projecta.game.core.base.object;

import java.lang.reflect.Field;
import java.util.HashMap;

public class GameObject {
    private HashMap<String, GameObjectComponent> componentHashMap = new HashMap<>();

    private static int globalId = 0;
    private int myId;
    private String name = "";

    public GameObject(String name) {
        this();
        this.name = name;
    }

    public GameObject() {
        globalId++;
        myId = globalId;
    }

    public String name() {return this.name;}

    public int id() {
        return this.myId;
    }

    public void addComponent(Class type, GameObjectComponent c) {
        this.componentHashMap.put(type.getName(), c);
    }

    public GameObjectComponent getComponent(Class type) {
        return this.componentHashMap.get(type.getName());
    }

    public void bindComponent() {
        Field[] fields = this.getClass().getFields();
        for (Field field : fields) {
            String name = field.getType().getName();
            if (this.componentHashMap.containsKey(name)) {
                field.setAccessible(true);
                try {
                    field.set(this, this.componentHashMap.get(name));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                field.setAccessible(false);
            }
        }
    }
}
