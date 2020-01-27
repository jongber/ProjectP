package com.jongber.game.desktop.room.controller;

import com.jongber.game.core.controller.adapter.InputControlAdapter;
import com.jongber.game.core.graphics.OrthoCameraWrapper;

public class CameraController extends InputControlAdapter {


    @Override
    public boolean touchDown(OrthoCameraWrapper camera, float worldX, float worldY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(OrthoCameraWrapper camera, float worldX, float worldY, int pointer, int button) {

        return false;
    }

    @Override
    public boolean touchDragged(OrthoCameraWrapper camera, float worldX, float worldY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(OrthoCameraWrapper camera, float worldX, float worldY) {
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
}
