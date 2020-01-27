package com.jongber.game.desktop.room.controller;

import com.jongber.game.core.GameObject;
import com.jongber.game.core.controller.adapter.InputControlAdapter;
import com.jongber.game.core.graphics.OrthoCameraWrapper;

import java.util.List;

public class ZoomController extends InputControlAdapter {
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

        //System.out.println("x[" + worldX + "] y[" + worldY + "]");

        return false;
    }

    @Override
    public boolean scrolled(OrthoCameraWrapper camera, int amount) {

        // up : -1
        // down : 1

        if (amount < 0 && camera.getZoom() <= 0.08f) {
            return true;
        }

        camera.addZoom(amount * 0.04f);

        return true;
    }

    @Override
    public void build(List<GameObject> graph) {
    }

    @Override
    public void dispose() {

    }
}
