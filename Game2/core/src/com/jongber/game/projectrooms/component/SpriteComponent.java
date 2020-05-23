package com.jongber.game.projectrooms.component;

import com.badlogic.gdx.math.Vector2;
import com.jongber.game.core.asset.AnimationAsset;
import com.jongber.game.core.graphics.VFAnimation;
import com.jongber.game.projectrooms.MainLayer;

public class SpriteComponent {
    public boolean flipX = false;
    public boolean flipY = false;

    public Vector2 scale = new Vector2(MainLayer.defaltScale, MainLayer.defaltScale);
    public float rotation = 0.0f;

    public Vector2 pivot;
    public VFAnimation animation = new VFAnimation();

    public SpriteComponent() {
    }

    public SpriteComponent(AnimationAsset asset, VFAnimation.PlayMode mode) {
        this.set(asset, mode);
    }

    public void set(AnimationAsset asset, VFAnimation.PlayMode mode) {
        this.animation.set(asset, mode);
        this.pivot = asset.getPivot();
    }

    public void flip(boolean x, boolean y) {
        this.flipX = x;
        this.flipY = y;
    }
}
