package com.jongber.game.core.sequence;

import com.jongber.game.core.GameLayer;
import com.jongber.game.core.controller.Controller;

public abstract class GameSequence extends Controller implements Controller.Updater {

    public abstract void create(GameLayer layer);

    public abstract void start();

    public abstract void end();

    public abstract boolean isEnded();
}
