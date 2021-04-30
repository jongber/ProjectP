package com.projecta.game.core.base.pipeline;

import com.projecta.game.core.base.layer.GameLayerController;
import com.projecta.game.core.base.object.GameObject;
import com.projecta.game.core.util.PackedArray;

public abstract class GameObjectPipeline extends GamePipeline {
    private PackedArray<GameObject> objList = new PackedArray<>();
    protected GameLayerController gameLayerController;

    public GameObjectPipeline(GameLayerController c) {
        this.gameLayerController = c;
    }

    public abstract boolean canAdd(GameObject obj);

    public abstract void updateInternal(GameObject obj, float elapsed);

    public abstract void onAdd(GameObject obj);

    public abstract void onRemove(GameObject obj);

    @Override
    public void update(float elapsed) {
        for (GameObject obj : this.objList) {
            this.updateInternal(obj, elapsed);
        }
    }

    public void addObject(GameObject obj) {
        if (this.canAdd(obj)) {
            this.objList.add(obj);
            this.onAdd(obj);
        }
    }

    public void removeObject(GameObject obj) {
        if (this.canAdd(obj)) {
            this.objList.remove(obj);
            this.onRemove(obj);
        }
    }
}
