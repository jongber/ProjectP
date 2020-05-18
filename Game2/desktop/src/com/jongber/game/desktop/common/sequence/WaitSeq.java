package com.jongber.game.desktop.common.sequence;

import com.jongber.game.core.GameLayer;
import com.jongber.game.core.sequence.GameSequence;

public class WaitSeq extends GameSequence {

    private float totElapsed;
    private float duration;

    public WaitSeq(float duration) {
        this.duration = duration;
    }

    @Override
    public void start(GameLayer layer) {

    }

    @Override
    public void end() {

    }

    @Override
    public boolean isEnded() {
        if (this.totElapsed >= this.duration) {
            return true;
        }

        return false;
    }

    @Override
    public void update(float elapsed) {
        this.totElapsed += elapsed;
    }

    @Override
    public void dispose() {

    }
}
