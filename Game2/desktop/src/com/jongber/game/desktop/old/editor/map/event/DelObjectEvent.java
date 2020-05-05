package com.jongber.game.desktop.old.editor.map.event;

import com.jongber.game.core.GameLayer;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.event.GameEvent;

public class DelObjectEvent extends GameEvent {

    GameLayer layer;
    GameObject object;

    public DelObjectEvent(GameLayer layer, GameObject object) {
        this.layer = layer;
        this.object = object;
    }

    @Override
    public void handle() {
        layer.removeObject(this.object);
    }
}
