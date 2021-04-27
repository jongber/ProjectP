package com.projecta.game.core.base;

import java.util.ArrayList;
import java.util.List;

public class GameLayer {
    private List<GameObjectController> objectControllers = new ArrayList<>();
    private GameLayerController lc = new GameLayerController();

    public GameLayer() {
    }

    public GameLayerController getLayerController() {
        return this.lc;
    }

    public void run(float elapsed) {
        for (GameObjectController c : this.objectControllers) {
            c.run(elapsed);
        }

        this.processLifecycle();
    }

    public void addObjectController(GameObjectController p) {
        this.objectControllers.add(p);
    }

    private void processLifecycle() {
        if (this.lc.objectLifeCache.isEmpty() == false) {

            for (GameObjectController c : this.objectControllers) {

                for (GameObject obj : this.lc.objectLifeCache.removeCache)
                    c.removeObject(obj);

                for (GameObject obj : this.lc.objectLifeCache.addCache)
                    c.addObject(obj);
            }

            this.lc.objectLifeCache.clear();
        }
    }
}