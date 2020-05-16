package com.jongber.game.desktop.editor.battle.seq;

import com.badlogic.gdx.math.Vector2;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.sequence.GameSequence;

public class GameObjectMoveSeq extends GameSequence {

    private GameObject object;
    private Vector2 to;

    public GameObjectMoveSeq(GameLayer layer, GameObject object, Vector2 to, float scale) {
        super(layer);
    }

    @Override
    public void start() {

    }

    @Override
    public void end() {

    }

    @Override
    public boolean isEnded() {
        return false;
    }

    @Override
    public void update(float elapsed) {

    }

    @Override
    public void dispose() {

    }
}
