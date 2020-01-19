package com.jongber.game.core;

import com.badlogic.gdx.Gdx;
import com.jongber.game.core.Entity;
import com.jongber.game.core.PackedArray;
import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;

import java.util.List;

public abstract class Controller {

    private PackedArray entities = new PackedArray();
    private Class componentType;

    public Controller(Class componentType) {
        this.componentType = componentType;
    }

    public void rebuild(List<Entity> entityList) {
        this.entities.clearAll();

        for (Entity entity : entityList) {
            if (entity.getComponent(this.componentType) != null) {
                this.entities.add(entity);
            }
        }
    }

    public abstract void update(float delta);
}
