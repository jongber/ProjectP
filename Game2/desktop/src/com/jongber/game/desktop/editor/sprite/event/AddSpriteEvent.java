package com.jongber.game.desktop.editor.sprite.event;

import com.jongber.game.core.GameLayer;
import com.jongber.game.core.event.GameEvent;

public class AddSpriteEvent extends GameEvent {

    private GameLayer layer;
    private String name;

    public AddSpriteEvent(GameLayer layer, String name) {
        this.layer = layer;
        this.name = name;
    }

    @Override
    public void handle() {

    }
}
