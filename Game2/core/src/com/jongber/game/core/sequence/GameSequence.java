package com.jongber.game.core.sequence;

import com.jongber.game.core.GameLayer;
import com.jongber.game.core.controller.Controller;

public abstract class GameSequence extends Controller implements Controller.Updater {

    protected GameLayer layer;

    public GameSequence(GameLayer layer) {
        this.layer = layer;
    }

    public GameLayer getLayer() {
        return this.layer;
    }

    public abstract void start();

    public abstract void end();

    public abstract boolean isEnded();
}
