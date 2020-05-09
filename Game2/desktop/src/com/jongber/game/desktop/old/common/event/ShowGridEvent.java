package com.jongber.game.desktop.old.common.event;

import com.jongber.game.core.GameLayer;
import com.jongber.game.core.event.GameEvent;
import com.jongber.game.desktop.common.controller.BlockGridRenderer;

public class ShowGridEvent extends GameEvent {

    private GameLayer layer;
    private boolean checked;

    public ShowGridEvent(GameLayer layer, boolean checked) {
        this.layer = layer;
        this.checked = checked;
    }

    @Override
    public void handle() {
        BlockGridRenderer render = (BlockGridRenderer)this.layer.getController(BlockGridRenderer.class);
        if (render == null) {
            return;
        }

        render.showGrid = this.checked;
    }
}
