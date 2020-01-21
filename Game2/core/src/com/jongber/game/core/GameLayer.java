package com.jongber.game.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jongber.game.core.controller.TransformController;
import com.jongber.game.core.graphics.OrthoCameraWrapper;

import java.util.ArrayList;
import java.util.List;

public class GameLayer {
    private SceneGraph graph = new SceneGraph();
    private TransformController transform = new TransformController();

    private List<Controller> controllers = new ArrayList<>();

    public void init() {
    }

    public void render(SpriteBatch batch, OrthoCameraWrapper camera) {
    }

    public void update(float delta) {
        this.build();

        this.transform.update(delta);
    }

    private void build() {
        if (this.graph.build()) {
            this.transform.build(this.graph.getGraph());

            for (Controller controller : this.controllers) {
                controller.build(this.graph.getGraph());
            }
        }
    }
}
