package com.jongber.game.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jongber.game.core.controller.TransformController;

import java.util.ArrayList;
import java.util.List;

public class GameLayer {
    private SceneGraph graph;
    private TransformController transform = new TransformController();

    private List<Controller> controllers = new ArrayList<>();

    public GameLayer() {
        this.graph = new SceneGraph();
    }

    public void render(SpriteBatch batch) {
    }

    public void update(float delta) {
        this.build();

        for (Controller controller : this.controllers) {
            controller.update(delta);
        }

    }

    public void registController(Controller controller) {
        this.controllers.add(controller);
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
