package com.jongber.game.desktop.editor.battle.seq;

import com.jongber.game.core.GameLayer;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.sequence.GameSequence;

public class ReturnOrgBattlePosSeq extends GameSequence {

    private GameObject object;

    public ReturnOrgBattlePosSeq(GameLayer layer, GameObject object) {
        super(layer);
        this.object = object;
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
