package com.projecta.game.core.base.pipeline;

import com.projecta.game.core.base.object.GameObject;
import com.projecta.game.core.util.PackedArray;

public abstract class GameObjectPipeline extends GamePipeline{
    protected PackedArray<GameObject> objects = new PackedArray<>();

    public abstract boolean canAddObject(GameObject obj);

    public void addObject(GameObject obj) {
        this.objects.add(obj);
    }

    public void removeObject(GameObject obj) {
        this.objects.remove(obj);
    }
}
