package com.jongber.projectp.game.data;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.jongber.projectp.asset.AnimationAsset;
import com.jongber.projectp.asset.SpriteAsset;
import com.jongber.projectp.graphics.VFAnimation;

public class SpriteComponent {
    private SpriteAsset asset;
    private VFAnimation animation = new VFAnimation();

    public SpriteComponent(SpriteAsset asset) {
        this.asset = asset;
    }

    public AnimationAsset getAnimationAsset(String name) {
        return this.asset.getAnimation(name);
    }

    public void setAnimation(String name, VFAnimation.PlayMode mode) throws Exception{
        AnimationAsset animAsset = this.asset.getAnimation(name);
        if (animAsset == null){
            throw new Exception("can't find animation[" + name + "] on [" + this.asset.getName() + "]");
        }

        this.animation.init(animAsset, mode);
    }

    public TextureRegion getNext(float elapsed) {
        return this.animation.getNext(elapsed);
    }

    public boolean canPlay() {
        return this.animation.canPlay();
    }

    public boolean isFinished() {
        return this.animation.getPlaybackCount() > 0
                && this.animation.getMode() == VFAnimation.PlayMode.ONCE;
    }

    public Vector2 getPivot() {
        return new Vector2(this.asset.getPivotX(), this.asset.getPivotY());
    }

    public int getPivotX() {
        return this.asset.getPivotX();
    }

    public int getPivotY() {
        return this.asset.getPivotY();
    }
}
