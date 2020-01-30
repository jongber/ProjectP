package com.jongber.game.projectz.controller;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.controller.Controller;
import com.jongber.game.core.graphics.OrthoCameraWrapper;

import java.util.ArrayList;
import java.util.List;

public class RoomPropertyController extends Controller implements Controller.Renderer {

    private List<GameObject> objs = new ArrayList<>();

    @Override
    public void render(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed) {

    }

    @Override
    public void build(List<GameObject> graph) {
        for (GameObject node : graph) {

        }
    }

    @Override
    public void dispose() {

    }
}
