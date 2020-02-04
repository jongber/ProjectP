package com.jongber.game.desktop.map.controller;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.controller.Controller;
import com.jongber.game.core.controller.adapter.InputControlAdapter;
import com.jongber.game.core.graphics.OrthoCameraWrapper;

import java.util.List;

public class RoomController extends InputControlAdapter implements Controller.PostRenderer {
    @Override
    public void build(List<GameObject> graph) {
    }

    @Override
    public void dispose() {
    }

    @Override
    public void postRender(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed) {

    }

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
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }
}
