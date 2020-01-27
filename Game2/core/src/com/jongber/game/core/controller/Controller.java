package com.jongber.game.core.controller;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.graphics.OrthoCameraWrapper;

import java.util.ArrayList;
import java.util.List;

public abstract class Controller {

    public interface Updater {
        void update(float elapsed);
    }

    public interface Renderer {
        void render(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed);
    }

    public interface PostRenderer {
        void postRender(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed);
    }

    public interface InputProcessor {
        boolean keyTyped(char character);
        boolean keyDown(int keycode);
        boolean keyUp(int keycode);
        boolean touchDown(OrthoCameraWrapper camera, float worldX, float worldY, int pointer, int button);
        boolean touchUp(OrthoCameraWrapper camera, float worldX, float worldY, int pointer, int button);
        boolean touchDragged(OrthoCameraWrapper camera, float worldX, float worldY, int pointer);
        boolean mouseMoved(OrthoCameraWrapper camera, float worldX, float worldY);
        boolean scrolled(OrthoCameraWrapper camera, int amount);
    }

    public abstract void build(List<GameObject> graph);

    public abstract void dispose();

    protected List<GameObject> buildSimple(List<GameObject> graph, Class type) {
        List<GameObject> objs = new ArrayList<>();

        for (GameObject obj : graph) {
            if (obj.hasComponent(type)) {
                objs.add(obj);
            }
        }

        return objs;
    }
}
