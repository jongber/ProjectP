package com.projecta.game.core.base.layer;

import com.projecta.game.core.base.object.GameObject;

import java.util.ArrayList;
import java.util.List;

public class GameLayerController {

    public static class GameObjectLifeCache {
        public List<GameObject> addCache = new ArrayList<>();
        public List<GameObject> removeCache = new ArrayList<>();

        public void reserveAdd(GameObject obj) {
            this.addCache.add(obj);
        }

        public void reserveRemove(GameObject obj) {
            this.removeCache.add(obj);
        }

        public void clear() {
            this.addCache.clear();
            this.removeCache.clear();
        }

        public boolean isEmpty() {
            return this.addCache.isEmpty() && this.removeCache.isEmpty();
        }
    }


    public GameObjectLifeCache objectLifeCache = new GameObjectLifeCache();

    public void addObject(GameObject obj) {
        this.objectLifeCache.reserveAdd(obj);
    }

    public void removeObject(GameObject obj) {
        this.objectLifeCache.reserveRemove(obj);
    }
}