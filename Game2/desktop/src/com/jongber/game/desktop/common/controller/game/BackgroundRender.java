package com.jongber.game.desktop.common.controller.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.controller.Controller;
import com.jongber.game.core.graphics.OrthoCameraWrapper;
import com.jongber.game.desktop.common.component.game.BackgroundComponent;

import java.util.List;

public class BackgroundRender extends Controller implements Controller.Renderer, Controller.GraphBuilder {

    List<GameObject> objects;

    @Override
    public void dispose() {

    }

    @Override
    public void render(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed) {

    }

    @Override
    public void build(List<GameObject> graph) {
        objects = buildSimple(graph, BackgroundComponent.class);
    }
}
