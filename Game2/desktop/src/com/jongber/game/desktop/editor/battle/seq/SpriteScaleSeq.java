package com.jongber.game.desktop.editor.battle.seq;

import com.jongber.game.core.GameLayer;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.sequence.GameSequence;
import com.jongber.game.desktop.common.component.SpriteComponent;

public class SpriteScaleSeq extends GameSequence {

    private float totElapsed;
    private GameObject object;
    private float duration;

    private float scale;
    private float spd;
    private SpriteComponent c;

    public SpriteScaleSeq(GameObject object, float scale, float duration) {
        this.object = object;
        this.duration = duration;
        this.c = this.object.getComponent(SpriteComponent.class);
        this.scale = scale;
    }

    @Override
    public void start(GameLayer layer) {
        spd = (scale - c.scale)/duration;
    }

    @Override
    public void end() {
        c.scale = scale;
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

        c.scale += this.spd * elapsed;
    }

    @Override
    public void dispose() {

    }
}