package com.jongber.game.desktop.common.controller.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.controller.Controller;
import com.jongber.game.core.graphics.OrthoCameraWrapper;
import com.jongber.game.desktop.common.component.game.ActorComponent;

import java.util.List;

public class ActorRender extends Controller implements Controller.Renderer, Controller.GraphBuilder {

    public enum RenderOrder {
        First,
        Second,
    }

    private List<GameObject> objects;
    private RenderOrder order;

    public ActorRender(RenderOrder order) {
        this.order = order;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void render(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed) {

    }

    @Override
    public void build(List<GameObject> graph) {
        this.objects = buildSimple(graph, ActorComponent.class);
    }
}
