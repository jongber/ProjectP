package com.jongber.game.desktop.editor.animation;

import com.jongber.game.core.GameObject;
import com.jongber.game.core.asset.AnimationAsset;
import com.jongber.game.core.event.GameEvent;
import com.jongber.game.core.graphics.VFAnimation;
import com.jongber.game.desktop.common.component.SpriteComponent;

public class AnimationSelectEvent extends GameEvent {

    AnimationView view;
    AnimationAsset asset;
    VFAnimation.PlayMode mode;

    public AnimationSelectEvent(AnimationView view, AnimationAsset asset, VFAnimation.PlayMode mode) {
        this.view = view;
        this.asset = asset;
        this.mode = mode;
    }

    @Override
    public void handle() {
        GameObject object = this.view.getAnimationTarget();
        SpriteComponent c = object.getComponent(SpriteComponent.class);
        c.set(this.asset, this.mode);
    }
}
