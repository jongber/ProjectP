package com.jongber.game;

import com.badlogic.gdx.Gdx;

public class Controller {

    public interface ControlImpl {
        void update(Entity entity, float delta);
    }

    private PackedArray entities = new PackedArray();
    private Class componentType;
    private ControlImpl impl;

    public Controller(Class componentType, ControlImpl impl) {
        this.impl = impl;
        this.componentType = componentType;
    }

    public void register(Entity entity) {
        if (entity.getComponent(this.componentType) == null)
            return;

        this.entities.add(entity);
    }

    public void unregister(Entity entity) {
        this.entities.remove(entity);
    }

    public void update(float delta) {
        for (Object object : entities.getArray()) {
            if (object instanceof Entity) {
                this.impl.update((Entity)object, delta);
            }
            else {
                Gdx.app.log("ERROR", "invalid entity instance");
            }
        }
    }
}
