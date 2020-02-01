package com.jongber.game.desktop.room.controller;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.controller.Controller;
import com.jongber.game.core.graphics.OrthoCameraWrapper;
import com.jongber.game.desktop.room.component.PropProperty;

import java.util.List;

public class RoomPropsController extends Controller implements Controller.Renderer {

    List<GameObject> objects;

    @Override
    public void build(List<GameObject> graph) {
        this.objects = this.buildSimple(graph, PropProperty.class);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void render(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed) {

    }
}
