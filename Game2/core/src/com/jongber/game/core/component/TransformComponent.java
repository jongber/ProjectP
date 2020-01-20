package com.jongber.game.core.component;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class TransformComponent {
    public Vector2 local = new Vector2();
    public List<Vector2> parentStack = new ArrayList<>();
    public Vector2 world = new Vector2();

    public Vector2 calcWorldPos() {
        this.world.setZero();

        for (Vector2 pos : this.parentStack) {
            this.world.add(pos.x, pos.y);
        }

        this.world.add(this.local.x, this.local.y);

        return this.world;
    }
}
