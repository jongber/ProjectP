package com.projecta.game.core.systems;

import com.projecta.game.core.GameObject;
import com.projecta.game.core.util.PackedArray;

public abstract class GameSystem {
    public abstract boolean canAddObject(GameObject obj);
}
