package com.jongber.game.core.sequence;

import com.jongber.game.core.GameLayer;

public interface GameSequence {

    void create(GameLayer layer);

    void ready();

    void update(float elapsed);

    boolean isEnded();
}
