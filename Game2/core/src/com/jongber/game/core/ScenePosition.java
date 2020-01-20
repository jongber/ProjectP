package com.jongber.game.core;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class ScenePosition {
    public Vector2 local = new Vector2();
    public List<Vector2> parentStack = new ArrayList<>();
    private Vector2 world = new Vector2();

    public Vector2 CalcWorldPos() {
        world.setZero();

        for (Vector2 parent : this.parentStack) {
            world.add(parent);
        }

        world.add(this.local);

        return this.world;
    }
}
