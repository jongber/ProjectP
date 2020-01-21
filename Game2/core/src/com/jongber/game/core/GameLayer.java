package com.jongber.game.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jongber.game.core.controller.Transformation;
import com.jongber.game.core.graphics.OrthoCameraWrapper;

import java.util.ArrayList;
import java.util.List;

public class GameLayer {

    public interface Updater {
        void update(float elapsed);
    }

    public interface Renderer {
        void render(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed);
    }

    private SceneGraph graph = new SceneGraph();
    private Transformation transform = new Transformation();

    private List<Controller> controllers = new ArrayList<>();
    private List<Renderer> renders = new ArrayList<>();
    private List<Updater> updaters = new ArrayList<>();

    public void init() {
        this.build();
    }

    public void registerController(Controller controller) {
        if (controller instanceof Renderer) {
            this.renders.add((Renderer)controller);
        }

        if (controller instanceof GameLayer.Updater) {
            this.updaters.add((Updater)controller);
        }

        this.controllers.add(controller);
    }

    public void render(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed) {
        for (Renderer renderer : this.renders) {
            renderer.render(batch, camera, elapsed);
        }
    }

    public void update(float elapsed) {
        this.build();

        this.transform.update(elapsed);
        for (Updater updater : this.updaters) {
            updater.update(elapsed);
        }
    }

    private void build() {
        boolean graphBuilt = this.graph.build();
        if (graphBuilt) {
            this.transform.build(this.graph.getGraph());

            for (Controller controller : this.controllers) {
                controller.build(this.graph.getGraph());
            }
        }
    }
}
