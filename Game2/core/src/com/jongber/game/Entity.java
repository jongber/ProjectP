package com.jongber.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Entity {
    private Entity parent = null;
    private List<Entity> children = new ArrayList<>();
    private HashMap<Class, Component> components = new HashMap<>();

    public void addChild(Entity child) {
        child.parent = this;
        children.add(child);
    }

    public List<Entity> getChildren() {
        return this.children;
    }

    public void removeChild(Entity child) {
        children.remove(child);
    }

    public void addComponent(Class type, Component component) {
        this.components.put(type, component);
    }

    public Component getComponent(Class type) {
        return this.components.get(type.getClass());
    }
}
