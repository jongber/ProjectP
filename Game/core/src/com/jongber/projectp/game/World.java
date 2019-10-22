package com.jongber.projectp.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jongber.projectp.asset.GameAsset;
import com.jongber.projectp.asset.json.GameSettingJson;
import com.jongber.projectp.common.Traverser;
import com.jongber.projectp.graphics.OrthoCameraWrapper;
import com.jongber.projectp.object.GameObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class World implements InputProcessor {

    public interface WorldLogic {
        void init(World world);
        void update(World world, float elapsed);
        void touchDown(int screenX, int screenY, int pointer, int button);
    }

    public OrthoCameraWrapper camera;
    public List<GameObject> sceneries = new ArrayList<>();
    private Set<GameObject> objects = new HashSet<>();
    private HashMap<Integer, GameObject> objectById = new HashMap<>();
    public List<WorldLogic> logics = new ArrayList<>();

    public World(GameSettingJson json) {
        this.camera = new OrthoCameraWrapper(json.viewport.w, json.viewport.h);
    }

    public void init() {
        for (int i = 0; i <logics.size(); ++i) {
            logics.get(i).init(this);
        }
    }

    public void update(SpriteBatch batch, float elapsed) {
        this.camera.update(batch);
        for (int i = 0; i <logics.size(); ++i) {
            logics.get(i).update(this, elapsed);
        }
    }

    public void addObject(GameObject object) {
        objects.add(object);
        objectById.put(object.getId(), object);
    }

    public GameObject getObject(int id) {
        return objectById.get(id);
    }

    public void removeObject(GameObject object) {
        objects.remove(object);
        objectById.remove(object.getId(), object);
    }

    public void forSceneries(Traverser<GameObject> traverser) {
        Iterator<GameObject> it = sceneries.iterator();
        while (it.hasNext()) {
            traverser.onTraverse(it.next());
        }
    }

    public void forObjects(Traverser<GameObject> traverser) {
        Iterator<GameObject> it = objects.iterator();
        while (it.hasNext()) {
            traverser.onTraverse(it.next());
        }
    }

    public void dispose() {
        GameAsset.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        for (int i = 0; i <logics.size(); ++i) {
            logics.get(i).touchDown(screenX, screenY, pointer, button);
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
