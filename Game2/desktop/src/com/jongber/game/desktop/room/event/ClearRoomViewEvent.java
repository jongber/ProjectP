package com.jongber.game.desktop.room.event;

import com.jongber.game.core.GameLayer;
import com.jongber.game.core.event.GameEvent;

public class ClearRoomViewEvent extends GameEvent {

    private final GameLayer layer;

    public ClearRoomViewEvent(GameLayer layer) {
        this.layer = layer;
    }

    @Override
    public void handle() {
        this.layer.resetObject();
    }
}
