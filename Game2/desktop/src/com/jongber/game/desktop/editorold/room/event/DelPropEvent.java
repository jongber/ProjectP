package com.jongber.game.desktop.editorold.room.event;

import com.jongber.game.core.GameLayer;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.event.GameEvent;

public class DelPropEvent extends GameEvent {

    private GameLayer layer;
    private GameObject delObject;

    public DelPropEvent(GameLayer layer, GameObject prop) {
        this.layer = layer;
        this.delObject = prop;
    }

    @Override
    public void handle() {
        this.layer.removeObject(this.delObject);
    }
}
