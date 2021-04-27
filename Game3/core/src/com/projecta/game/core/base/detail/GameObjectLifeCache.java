package com.projecta.game.core.base.detail;

import com.projecta.game.core.base.GameObject;

import java.util.ArrayList;
import java.util.List;

public class GameObjectLifeCache {
    public List<GameObject> addCache = new ArrayList<>();
    public List<GameObject> removeCache = new ArrayList<>();

    public void reserveAdd(GameObject obj) {
        this.addCache.add(obj);
    }

    public void reserveRemove(GameObject obj) {
        this.addCache.remove(obj);
    }

    public void clear() {
        this.addCache.clear();
        this.removeCache.clear();
    }

    public boolean isEmpty() {
        return this.addCache.isEmpty() && this.removeCache.isEmpty();
    }
}