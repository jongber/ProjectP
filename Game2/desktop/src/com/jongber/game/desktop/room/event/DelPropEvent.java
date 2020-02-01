package com.jongber.game.desktop.room.event;

import com.jongber.game.core.GameLayer;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.event.GameEvent;

public class DelPropEvent extends GameEvent {

    private GameLayer layer;
    private GameObject delObject;
    private String roomName;

    public DelPropEvent(GameLayer layer, String roomName, GameObject prop) {
        this.layer = layer;
        this.delObject = prop;
        this.roomName = roomName;
    }

    @Override
    public void handle() {
        this.layer.removeObject(this.roomName, this.delObject);
    }
}
