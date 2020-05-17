package com.jongber.game.desktop.editor.battle.seq;

import com.jongber.game.core.GameLayer;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.asset.AnimationAsset;
import com.jongber.game.core.graphics.VFAnimation;
import com.jongber.game.core.sequence.GameSequence;
import com.jongber.game.desktop.common.component.SpriteComponent;

public class ChangeAnimSeq extends GameSequence {

    private GameObject object;
    private AnimationAsset asset;
    private VFAnimation.PlayMode mode;

    public ChangeAnimSeq(GameObject object, AnimationAsset asset, VFAnimation.PlayMode mode) {
        this.object = object;
        this.asset = asset;
        this.mode = mode;
    }

    @Override
    public void start(GameLayer layer) {
        SpriteComponent c = this.object.getComponent(SpriteComponent.class);
        c.set(asset, mode);
    }

    @Override
    public void end() {

    }

    @Override
    public boolean isEnded() {
        return true;
    }

    @Override
    public void update(float elapsed) {

    }

    @Override
    public void dispose() {

    }
}
