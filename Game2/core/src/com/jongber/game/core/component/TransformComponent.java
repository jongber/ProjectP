package com.jongber.game.core.component;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;


public class TransformComponent {
    public Matrix3 local = new Matrix3();
    public Matrix3 world = new Matrix3();

    private Vector2 worldPos = new Vector2();
    private Vector2 localPos = new Vector2();

    public Vector2 getWorldPos() {
        return this.world.getTranslation(this.worldPos);
    }

    public Vector2 getLocalPos() {
        return this.local.getTranslation(this.localPos);
    }
}
