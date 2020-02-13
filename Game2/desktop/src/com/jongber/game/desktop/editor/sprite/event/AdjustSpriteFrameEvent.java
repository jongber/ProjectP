package com.jongber.game.desktop.editor.sprite.event;

import com.jongber.game.core.GameLayer;
import com.jongber.game.core.event.GameEvent;

public class AdjustSpriteFrameEvent extends GameEvent {

    private GameLayer layer;
    private int[] frames;

    public AdjustSpriteFrameEvent(GameLayer layer, int[] frames) {
        this.layer = layer;
        this.frames = frames;
    }

    @Override
    public void handle() {

    }
}
