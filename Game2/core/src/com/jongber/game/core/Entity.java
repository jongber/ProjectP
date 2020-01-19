package com.jongber.game.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Entity {

    public interface EntityEventHandler {
        void handleChanged();
    }

    private EntityEventHandler handler;
    private Entity parent = null;
    private List<Entity> children = new ArrayList<>();
    private HashMap<Class, Component> components = new HashMap<>();

    public Entity(EntityEventHandler handler) {
        this.handler = handler;
    }

    public void addChild(Entity child) {
        child.parent = this;
        children.add(child);
        this.handler.handleChanged();
    }

    public List<Entity> getChildren() {
        return this.children;
    }

    public void removeChild(Entity child) {
        children.remove(child);
        this.handler.handleChanged();
    }

    public void addComponent(Class type, Component component) {
        this.components.put(type, component);
    }

    public Component getComponent(Class type) {
        return this.components.get(type.getClass());
    }
}
