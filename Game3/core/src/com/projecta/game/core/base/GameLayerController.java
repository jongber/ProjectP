package com.projecta.game.core.base;

import com.projecta.game.core.base.detail.GameObjectLifeCache;

public class GameLayerController {
    public GameObjectLifeCache objectLifeCache = new GameObjectLifeCache();

    public void addObject(GameObject obj) {
        this.objectLifeCache.reserveAdd(obj);
    }

    public void removeObject(GameObject obj) {
        this.objectLifeCache.reserveRemove(obj);
    }
}
