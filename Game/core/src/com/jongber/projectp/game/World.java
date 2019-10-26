package com.jongber.projectp.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jongber.projectp.asset.GameAsset;
import com.jongber.projectp.asset.json.GameSettingJson;
import com.jongber.projectp.common.JoinTraverser;
import com.jongber.projectp.common.PackedArray;
import com.jongber.projectp.common.Traverser;
import com.jongber.projectp.graphics.OrthoCameraWrapper;
import com.jongber.projectp.object.GameObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
* this is data class, do not add world logic!!!
* */
public class World implements InputProcessor {

    public interface WorldLogic {
        void init(World world);
        void update(World world, float elapsed);
        void touchDown(int screenX, int screenY, int pointer, int button);
    }

    public static GameSettingJson Setting;
    public OrthoCameraWrapper camera;
    public List<GameObject> sceneries = new ArrayList<>();
    public List<WorldLogic> logics = new ArrayList<>();

    private PackedArray objects = new PackedArray();

    public World(GameSettingJson json) {
        World.Setting = json;
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
    }

    public boolean isContained(GameObject object) {
        return this.objects.isContained(object);
    }

    public int gameObjectCount() {
        return this.objects.size();
    }

    public void removeObject(GameObject object) {
        objects.remove(object);
    }

    public void forSceneries(Traverser<GameObject> traverser) {
        Iterator<GameObject> it = sceneries.iterator();
        while (it.hasNext()) {
            traverser.onTraverse(it.next());
        }
    }

    public void forObjects(Traverser<GameObject> traverser) {
        Object[] array = this.objects.getArray();
        for (int i = 0; i < this.objects.size(); ++i) {
            traverser.onTraverse((GameObject) array[i]);
        }
    }

    public void forJoinObjects(JoinTraverser<GameObject> join) {
        Object[] array = this.objects.getArray();
        int size = this.objects.size();
        for(int i = 0; i < size; ++i) {
            GameObject target1 = (GameObject)array[i];
            for (int j = i + 1; j < size; ++j) {
                GameObject target2 = (GameObject)array[j];
                join.onJoin(target1, target2);
            }
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
