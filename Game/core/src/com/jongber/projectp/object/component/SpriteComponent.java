package com.jongber.projectp.object.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.jongber.projectp.asset.AnimationAsset;
import com.jongber.projectp.asset.SpriteAsset;
import com.jongber.projectp.graphics.VFAnimation;
import com.jongber.projectp.object.GameObject;

public class SpriteComponent {
    private SpriteAsset asset;
    private VFAnimation animation = new VFAnimation();

    public SpriteComponent(SpriteAsset asset) {
        this.asset = asset;
    }

    public AnimationAsset getAnimationAsset(String name) {
        return this.asset.getAnimation(name);
    }

    public String getAnimationName() {
        return this.animation.getName();
    }

    public boolean isAnimation(String name) {
        return this.animation.getName().compareTo(name) == 0;
    }

    public void setAnimation(String name, VFAnimation.PlayMode mode) {
        AnimationAsset animAsset = this.asset.getAnimation(name);
        if (animAsset == null){
            Gdx.app.log("DEBUG", "can't find animation[" + name + "]");
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

    public static void changeAnimation(GameObject object, String change, VFAnimation.PlayMode mode) {
        SpriteComponent sprite = object.getComponent(SpriteComponent.class);
        if (sprite != null && sprite.isAnimation(change) == false) {
            sprite.setAnimation(change, mode);
        }
    }
}
