package com.jongber.game.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class Engine implements GameObject.Callback {
    private boolean modified = false;
    private SceneGraph graph;
    private List<Controller> controllers = new ArrayList<>();

    public Engine() {
        this.graph = new SceneGraph(new GameObject(this));
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
        if (this.modified == false)
            return;

        this.graph.build();
        for (Controller controller : this.controllers) {
            controller.build(this.graph.getGraph());
        }

        this.modified = false;
    }

    @Override
    public void modified(GameObject object) {
        this.modified = true;
    }
}
