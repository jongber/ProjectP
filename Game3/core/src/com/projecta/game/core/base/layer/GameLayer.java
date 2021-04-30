package com.projecta.game.core.base.layer;

import com.projecta.game.core.base.object.GameObject;
import com.projecta.game.core.base.pipeline.GameObjectPipeline;
import com.projecta.game.core.base.pipeline.GamePipeline;

import java.util.ArrayList;
import java.util.List;

public class GameLayer {
    private List<GamePipeline> pipelines = new ArrayList<>();
    private GameLayerController lController = new GameLayerController();

    public GameLayer() {
    }

    public GameLayerController getLayerController() {
        return this.lController;
    }

    public void update(float elapsed) {
        for (GamePipeline p : this.pipelines) {
            p.update(elapsed);
        }

        this.processLifecycle();
    }

    public void addGamePipeline(GamePipeline p) {
        this.pipelines.add(p);
    }

    public void dispose() {
        for (GamePipeline p : this.pipelines) {
            p.dispose();
        }
    }

    private void processLifecycle() {
        if (this.lController.objectLifeCache.isEmpty() == false) {

            for (GamePipeline p : this.pipelines) {

                if (p instanceof GameObjectPipeline) {
                    GameObjectPipeline op = (GameObjectPipeline) p;

                    for (GameObject obj : this.lController.objectLifeCache.removeCache)
                        op.removeObject(obj);

                    for (GameObject obj : this.lController.objectLifeCache.addCache)
                        op.addObject(obj);
                }
            }

            this.lController.objectLifeCache.clear();
        }
    }
}