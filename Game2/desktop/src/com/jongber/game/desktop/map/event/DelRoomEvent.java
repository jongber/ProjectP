package com.jongber.game.desktop.map.event;

import com.jongber.game.core.GameLayer;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.event.GameEvent;

public class DelRoomEvent extends GameEvent {

    GameLayer layer;
    GameObject object;

    public DelRoomEvent(GameLayer layer, GameObject object) {
        this.layer = layer;
        this.object = object;
    }

    @Override
    public void handle() {
        layer.removeObject(this.object);
    }
}