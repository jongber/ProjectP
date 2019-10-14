package com.jongber.projectp.game.data;

import com.badlogic.gdx.math.Vector2;
import com.jongber.projectp.asset.AnimationAsset;
import com.jongber.projectp.asset.SpriteAsset;

public class SpriteComponent {
    private SpriteAsset asset;

    public SpriteComponent(SpriteAsset asset) {
        this.asset = asset;
    }

    public AnimationAsset getAnimationAsset(String name) {
        return this.asset.getAnimation(name);
    }

    public Vector2 getPivot() {
        return new Vector2(this.asset.getPivotX(), this.asset.getPivotY());
    }
}
