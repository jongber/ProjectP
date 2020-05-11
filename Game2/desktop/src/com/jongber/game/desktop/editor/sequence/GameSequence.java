package com.jongber.game.desktop.editor.sequence;

import com.jongber.game.core.GameLayer;

public interface GameSequence {

    void create(GameLayer layer);

    void update(float elapsed);

    boolean ended();
}
