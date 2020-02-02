package com.jongber.game.desktop.room.controller;

import com.badlogic.gdx.math.Vector2;
import com.jongber.game.core.controller.adapter.InputControlAdapter;
import com.jongber.game.core.graphics.OrthoCameraWrapper;

public class CameraController extends InputControlAdapter {
    private boolean wheel = false;
    private final Vector2 pressed = new Vector2();

    @Override
    public boolean touchDown(OrthoCameraWrapper camera, float worldX, float worldY, int pointer, int button) {

        if (button == 2) {
            wheel = true;
            this.pressed.set(worldX, worldY);
            return true;
        }

        return false;
    }

    @Override
    public boolean touchUp(OrthoCameraWrapper camera, float worldX, float worldY, int pointer, int button) {

        if (button == 2 && this.wheel) {
            this.pressed.setZero();
            this.wheel = false;
            return true;
        }

        return false;
    }

    @Override
    public boolean touchDragged(OrthoCameraWrapper camera, float worldX, float worldY, int pointer) {

        if (wheel) {
            Vector2 drag = new Vector2(worldX, worldY).sub(this.pressed);
            float len = drag.len();
            drag.nor();
            drag.scl(-1.0f * len * 0.5f);

            camera.addPosition(drag);
            this.pressed.set(worldX, worldY);

            return true;
        }

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
