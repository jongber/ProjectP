package com.projecta.game.core.base;

import java.util.ArrayList;
import java.util.List;

public class GameLayer {
    private List<GameObjectController> oControllers = new ArrayList<>();
    private GameLayerController lController = new GameLayerController();

    public GameLayer() {
    }

    public GameLayerController getLayerController() {
        return this.lController;
    }

    public void update(float elapsed) {
        for (GameObjectController c : this.oControllers) {
            c.update(elapsed);
        }

        this.processLifecycle();
    }

    public void addObjectController(GameObjectController p) {
        this.oControllers.add(p);
    }

    public void dispose() {
        for (GameObjectController c : this.oControllers) {
            c.dispose();
        }
    }

    private void processLifecycle() {
        if (this.lController.objectLifeCache.isEmpty() == false) {

            for (GameObjectController c : this.oControllers) {

                for (GameObject obj : this.lController.objectLifeCache.removeCache)
                    c.removeObject(obj);

                for (GameObject obj : this.lController.objectLifeCache.addCache)
                    c.addObject(obj);
            }

            this.lController.objectLifeCache.clear();
        }
    }
}