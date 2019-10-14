package com.jongber.projectp.game;

import com.badlogic.gdx.math.Vector2;

public class GameObject {
    private Vector2 transform;

    public GameObject() {
        this.transform = new Vector2();
    }

    public Vector2 getTransform() {
        return this.transform;
    }
}
