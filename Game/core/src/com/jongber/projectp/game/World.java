package com.jongber.projectp.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jongber.projectp.asset.GameAsset;
import com.jongber.projectp.asset.json.GameSettingJson;
import com.jongber.projectp.common.Traverser;
import com.jongber.projectp.graphics.OrthoCameraWrapper;
import com.jongber.projectp.object.GameObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class World {
    public OrthoCameraWrapper camera;
    public List<GameObject> sceneries = new ArrayList<>();
    public Set<GameObject> objects = new HashSet<>();

    public World(GameSettingJson json) {
        this.camera = new OrthoCameraWrapper(json.viewport.w, json.viewport.h);
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
}
