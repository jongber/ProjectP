package com.jongber.game.core.controller;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.graphics.OrthoCameraWrapper;

import java.util.List;

public abstract class Controller {

    public interface Updater {
        void update(float elapsed);
    }

    public interface Renderer {
        void render(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed);
    }

    public abstract void build(List<GameObject> graph);
}
