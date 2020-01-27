package com.jongber.game.core.controller.adapter;

import com.jongber.game.core.GameObject;
import com.jongber.game.core.controller.Controller;
import com.jongber.game.core.graphics.OrthoCameraWrapper;

import java.util.List;

public class InputControlAdapter extends Controller implements Controller.InputProcessor {
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean touchDown(float worldX, float worldY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(float worldX, float worldY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(float worldX, float worldY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(float worldX, float worldY) {
        return false;
    }

    @Override
    public boolean scrolled(OrthoCameraWrapper camera, int amount) {
        return false;
    }

    @Override
    public void build(List<GameObject> graph) {

    }

    @Override
    public void dispose() {

    }
}
