package com.jongber.game.desktop.common.component;

import com.badlogic.gdx.math.Vector2;
import com.jongber.game.core.asset.AnimationAsset;
import com.jongber.game.core.component.Component;
import com.jongber.game.core.graphics.VFAnimation;

public class SpriteComponent extends Component {

    public boolean flipX = false;
    public boolean flipY = false;

    public Vector2 scale = new Vector2(1.0f, 1.0f);
    public float rotation = 0.0f;

    public AnimationAsset asset;
    public VFAnimation animation = new VFAnimation();

    public void set(AnimationAsset asset, VFAnimation.PlayMode mode) {
        this.asset = asset;
        this.animation.set(asset, mode);
    }

    public void flip(boolean x, boolean y) {
        this.flipX = x;
        this.flipY = y;
    }
}
