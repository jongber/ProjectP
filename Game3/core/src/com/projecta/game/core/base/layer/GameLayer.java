package com.projecta.game.core.base.layer;

import com.badlogic.gdx.InputProcessor;
import com.projecta.game.core.base.object.GameObject;
import com.projecta.game.core.base.pipeline.GameObjectPipeline;
import com.projecta.game.core.base.pipeline.GamePipeline;
import com.projecta.game.core.util.PackedArray;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

public class GameLayer implements InputProcessor {
    private PackedArray<GameObject> objects = new PackedArray<>();
    private HashMap<String, PackedArray<GameObject>> nameMap = new HashMap<>();

    private GameLayerController lc = new GameLayerController();
    private GameLayerMessageHandler handler = new GameLayerMessageHandler();

    private List<GamePipeline> pipelines = new ArrayList<>();
    private List<GameObjectPipeline> objectPipelines = new ArrayList<>();
    private List<GamePipeline.InputProcessor> inputProcessors = new ArrayList<>();
    private List<GamePipeline.Updater> updaters = new ArrayList<>();
    private List<GamePipeline.Renderer> renderers = new ArrayList<>();

    public GameLayer() {
    }

    public void addPipeline(GamePipeline pipeline) {
        this.pipelines.add(pipeline);

        if (pipeline instanceof GameObjectPipeline) {
            this.objectPipelines.add((GameObjectPipeline)pipeline);
        }

        if (pipeline instanceof GamePipeline.Updater) {
            this.updaters.add((GamePipeline.Updater)pipeline);
        }

        if (pipeline instanceof GamePipeline.InputProcessor) {
            this.inputProcessors.add((GamePipeline.InputProcessor)pipeline);
        }

        if (pipeline instanceof GamePipeline.Renderer) {
            this.renderers.add((GamePipeline.Renderer)pipeline);
        }
    }

    public void postMessage(GameLayerMessage msg) {
        this.handler.post(msg);
    }

    public void update(float elapsed) {

        this.handler.handle(this);

        for (GamePipeline.Updater updater : this.updaters) {
            updater.update(elapsed);
        }

        for (GamePipeline.Renderer renderer : this.renderers) {
            renderer.render(elapsed);
        }

        this.processAddRemoveObjects();
    }

    public void dispose() {
        for (GamePipeline p : this.pipelines) {
            p.dispose();
        }
    }

    public void resize(int width, int height) {
        for (GamePipeline.Renderer renderer : this.renderers) {
            renderer.resize(width, height);
        }
    }

    public void addObject(GameObject obj) {
        this.lc.addObject(obj);
    }

    public void removeObject(GameObject obj) {
        this.lc.removeObject(obj);
    }

    public void removeAllObjects() {
        for (GameObject obj : this.objects) {
            this.removeObject(obj);
        }
    }

    public PackedArray<GameObject> getObjects() {
        return this.objects;
    }

    public PackedArray<GameObject> getObjects(String name) {
        return this.nameMap.get(name);
    }

    public GameObject getObjectFirst(String name) {

        PackedArray<GameObject> arr = this.getObjects(name);

        if (arr != null && arr.size() > 0) {
            return arr.get(0);
        }

        return null;
    }

    private void processAddRemoveObjects() {

        if (this.lc.objectLifeCache.isEmpty()) {
            return;
        }

        for (GameObjectPipeline p : this.objectPipelines) {
            for (GameObject o : this.lc.objectLifeCache.removeCache) {
                p.removeObject(o);
                this.objects.remove(o);
                PackedArray<GameObject> arr = this.getObjects(o.name());
                if (arr != null) {
                    arr.remove(o);
                }
            }

            for (GameObject o : this.lc.objectLifeCache.addCache) {
                p.addObject(o);
                this.objects.add(o);

                PackedArray<GameObject> arr = this.getObjects(o.name());
                if (arr != null) {
                    arr.add(o);
                }
                else {
                    arr = new PackedArray<>();
                    arr.add(o);
                    this.nameMap.put(o.name(), arr);
                }
            }
        }

        this.lc.objectLifeCache.clear();
    }

    @Override
    public boolean keyDown(int keycode) {
        for (GamePipeline.InputProcessor p : this.inputProcessors) {
            if (p.keyDown(keycode)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        for (GamePipeline.InputProcessor p : this.inputProcessors) {
            if (p.keyUp(keycode)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        for (GamePipeline.InputProcessor p : this.inputProcessors) {
            if (p.keyTyped(character)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        for (GamePipeline.InputProcessor p : this.inputProcessors) {
            if (p.touchDown(screenX, screenY, pointer, button)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        for (GamePipeline.InputProcessor p : this.inputProcessors) {
            if (p.touchUp(screenX, screenY, pointer, button)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        for (GamePipeline.InputProcessor p : this.inputProcessors) {
            if (p.touchDragged(screenX, screenY, pointer)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        for (GamePipeline.InputProcessor p : this.inputProcessors) {
            if (p.mouseMoved(screenX, screenY)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        for (GamePipeline.InputProcessor p : this.inputProcessors) {
            if (p.scrolled(amountX, amountY)) {
                return true;
            }
        }

        return false;
    }
}