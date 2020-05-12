package com.jongber.game.core.sequence;

import com.jongber.game.core.GameLayer;

public interface GameSequence {

    void create(GameLayer layer);

    void start();

    void update(float elapsed);

    void end();

    boolean isEnded();
}
