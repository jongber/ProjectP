package com.jongber.game.desktop.old.common.event;

import com.jongber.game.core.GameLayer;
import com.jongber.game.core.event.GameEvent;

public class ClearAllEvent extends GameEvent {

    private final GameLayer layer;

    public ClearAllEvent(GameLayer layer) {
        this.layer = layer;
    }

    @Override
    public void handle() {
        this.layer.resetObject();
    }
}
