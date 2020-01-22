package com.jongber.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jongber.game.core.controller.SceneGraph;
import com.jongber.game.core.controller.Transformation;
import com.jongber.game.core.graphics.OrthoCameraWrapper;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.ArrayList;
import java.util.List;

public class GameLayer {

    public interface Updater {
        void update(float elapsed);
    }

    public interface Renderer {
        void render(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed);
    }

    private OrthoCameraWrapper cameraWrapper;

    private SceneGraph graph = new SceneGraph();
    private Transformation transform = new Transformation();

    private List<Controller> controllers = new ArrayList<>();
    private List<Renderer> renders = new ArrayList<>();
    private List<Updater> updaters = new ArrayList<>();

    private List<GameObject> objects = new ArrayList<>();
    private boolean modified = true;

    public GameLayer() {
        this.cameraWrapper = new OrthoCameraWrapper(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public GameLayer(Viewport viewport) {
        this.cameraWrapper = new OrthoCameraWrapper(viewport);
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

    public void render(SpriteBatch batch, float elapsed) {

        this.cameraWrapper.update(batch);

        for (Renderer renderer : this.renders) {
            renderer.render(batch, this.cameraWrapper, elapsed);
        }
    }

    public void update(float elapsed) {
        this.build();

        this.transform.update(elapsed);
        for (Updater updater : this.updaters) {
            updater.update(elapsed);
        }
    }

    public void addObject(GameObject object) {
        if (object.getParent() != null) {
            return;
        }

        this.objects.add(object);
        this.modified = true;
    }

    public void removeObject(GameObject object) {
        if (object.getParent() != null) {
            return;
        }

        this.objects.remove(object);
        this.modified = true;
    }

    public void resize (int width, int height) {
        this.cameraWrapper.resize(width, height);
    }

    public void dispose() {
    }

    private void build() {
        if (this.modified == false)
            return;

        this.graph.build(this.objects);
        this.transform.build(this.graph.getGraph());

        for (Controller controller : this.controllers) {
            controller.build(this.graph.getGraph());
        }

        this.modified = false;
    }
}
