package com.jongber.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jongber.game.core.controller.Controller;
import com.jongber.game.core.controller.SceneGraph;
import com.jongber.game.core.controller.Transformation;
import com.jongber.game.core.event.GameEvent;
import com.jongber.game.core.event.GameEventHandler;
import com.jongber.game.core.graphics.OrthoCameraWrapper;
import com.jongber.game.core.util.PackedArray;

import java.util.ArrayList;
import java.util.List;

public class GameLayer {

    private OrthoCameraWrapper cameraWrapper;

    private SceneGraph graph = new SceneGraph();
    private Transformation transform = new Transformation();

    private List<Controller> controllers = new ArrayList<>();
    private List<Controller.Renderer> renders = new ArrayList<>();
    private List<Controller.PostRenderer> postRenders = new ArrayList<>();
    private List<Controller.Updater> updaters = new ArrayList<>();
    private List<Controller.InputProcessor> inputProcessors = new ArrayList<>();

    private PackedArray<GameObject> objects = new PackedArray<>();
    private boolean modified = true;

    private GameLayerInput layerInput;

    private GameEventHandler eventHandler = new GameEventHandler();

    public GameLayer() {
        this.cameraWrapper = new OrthoCameraWrapper(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.layerInput = new GameLayerInput(this.inputProcessors, this.cameraWrapper);
    }

    public GameLayer(Viewport viewport) {
        this.cameraWrapper = new OrthoCameraWrapper(viewport);
        this.layerInput = new GameLayerInput(this.inputProcessors, this.cameraWrapper);
    }

    public GameLayerInput getInput() {
        return this.layerInput;
    }

    public void registerController(Controller controller) {
        if (controller instanceof Controller.Renderer) {
            this.renders.add((Controller.Renderer)controller);
        }

        if (controller instanceof Controller.PostRenderer) {
            this.postRenders.add((Controller.PostRenderer)controller);
        }

        if (controller instanceof Controller.Updater) {
            this.updaters.add((Controller.Updater)controller);
        }

        if (controller instanceof Controller.InputProcessor) {
            this.inputProcessors.add((Controller.InputProcessor)controller);
        }

        this.controllers.add(controller);
        this.modified = true;
    }

    public void unregisterController(Controller controller) {
        if (controller instanceof Controller.Renderer) {
            this.renders.remove(controller);
        }

        if (controller instanceof Controller.Updater) {
            this.updaters.remove(controller);
        }

        if (controller instanceof Controller.InputProcessor) {
            this.inputProcessors.remove(controller);
        }

        if (controller instanceof Controller.PostRenderer) {
            this.postRenders.remove(controller);
        }

        this.controllers.remove(controller);
        this.modified = true;
    }

    public Controller getController(Class type) {
        for (Controller controller : this.controllers) {
            if (type.isInstance(controller)) {
                return controller;
            }
        }

        return null;
    }

    public void render(SpriteBatch batch, float elapsed) {

        this.cameraWrapper.update(batch);

        for (Controller.Renderer renderer : this.renders) {
            renderer.render(batch, this.cameraWrapper, elapsed);
        }

        for (Controller.PostRenderer renderer : this.postRenders) {
            renderer.postRender(batch, this.cameraWrapper, elapsed);
        }
    }

    public void update(float elapsed) {

        this.eventHandler.handle();

        this.build();

        this.transform.update(elapsed);
        for (Controller.Updater updater : this.updaters) {
            updater.update(elapsed);
        }
    }

    public void addObject(GameObject object) {
        if (object.getParent() != null) {
            Gdx.app.error("GameLayer", "cannot add gameobject, object has parent " + object.name);
            return;
        }

        this.objects.add(object);
        this.modified = true;
    }

    public void removeObject(GameObject object) {
        if (object.getParent() != null) {
            Gdx.app.error("GameLayer", "cannot remove gameobject, object has parent " + object.name);
            return;
        }

        this.objects.remove(object);
        this.modified = true;
    }

    public void addObject(GameObject parent, GameObject child) {
        if (parent == null) {
            Gdx.app.error("GameLayer", "cannot add gameobjec parent is null" + child.name);
            return;
        }

        parent.addChild(child);
        this.modified = true;
    }

    public void removeObject(GameObject parent, GameObject child) {
        parent.removeChild(child);
        this.modified = true;
    }

    public void resetObject() {
        this.objects.clearAll();

        this.modified = true;
    }

    public List<GameObject> gatherObject(String name) {
        List<GameObject> objs = new ArrayList<>();

        for (GameObject obj : this.objects) {
            if (obj.name.equals(name)) {
                objs.add(obj);
            }
        }

        return objs;
    }

    public <T> List<T> gatherObject(Class<T> type) {
        List<T> objs = new ArrayList<>();
        for (GameObject obj : this.objects) {
            if (type.isInstance(obj)) {
                objs.add(type.cast(obj));
            }
        }

        return objs;
    }

    public void post(GameEvent event) {
        this.eventHandler.post(event);
    }

    public void resize (int width, int height) {
        this.cameraWrapper.resize(width, height);
    }

    public void dispose() {
        for (Controller controller : this.controllers) {
            controller.dispose();
        }
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
