package com.projecta.game.core.base;

import com.projecta.game.core.util.PackedArray;

public abstract class GameObjectController {
    private PackedArray<GameObject> objList = new PackedArray<>();
    protected GameLayerController gameLayerController;

    public GameObjectController(GameLayerController c) {
        this.gameLayerController = c;
    }

    public abstract boolean canAdd(GameObject obj);

    public abstract void runInternal(GameObject obj, float elapsed);

    public abstract void onAdd(GameObject obj);

    public abstract void onRemove(GameObject obj);

    public void run(float elapsed) {
        for (GameObject obj : this.objList) {
            this.runInternal(obj, elapsed);
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
