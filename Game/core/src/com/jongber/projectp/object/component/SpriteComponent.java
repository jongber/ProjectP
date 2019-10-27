package com.jongber.projectp.object.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.jongber.projectp.asset.AnimationAsset;
import com.jongber.projectp.asset.SpriteAsset;
import com.jongber.projectp.graphics.VFAnimation;
import com.jongber.projectp.object.GameObject;

public class SpriteComponent {
    public static final int SpriteLayer = 2;
    private final int layerCnt;
    private SpriteAsset[] assets;
    private VFAnimation[] animations;

    public SpriteComponent(int layerCnt) {
        this.layerCnt = layerCnt;
        this.assets = new SpriteAsset[layerCnt];
        this.animations = new VFAnimation[layerCnt];

        for (int i = 0; i < layerCnt; ++i) {
            this.animations[i] = new VFAnimation();
        }
    }

    public void addAsset(int layer, SpriteAsset asset) {
        if (this.layerCnt <= layer) {
            Gdx.app.log("DEBUG", "invalid sprite layer " + layer);
            return;
        }

        assets[layer] = asset;
    }

    public boolean isAnimation(String name) {
        for (int i = 0; i < this.layerCnt; ++i) {
            if (this.animations[i].getName().compareTo(name) != 0) {
                return false;
            }
        }

        return true;
    }

    public void setAnimation(String name, VFAnimation.PlayMode mode) {
        for (int i = 0; i < this.layerCnt; ++i) {
            setAnimation(i, name, mode);
        }
    }

    public void setAnimation(int layer, String name, VFAnimation.PlayMode mode) {
        if (this.layerCnt <= layer) {
            return;
        }

        AnimationAsset animAsset = this.assets[layer].getAnimation(name);
        if (animAsset == null){
            Gdx.app.log("DEBUG", "can't find animation[" + name + "]");
        }

        this.animations[layer].init(animAsset, mode);
    }

    public TextureRegion getNext(int layer, float elapsed) {
        if (this.layerCnt <= layer) {
            return null;
        }

        return this.animations[layer].getNext(elapsed);
    }

    public boolean canPlay() {
        return this.animations[0].canPlay();
    }

    public boolean isFinished() {
        return this.animations[0].getPlaybackCount() > 0
                && this.animations[0].getMode() == VFAnimation.PlayMode.ONCE;
    }

    public Vector2 getPivot() {
        return new Vector2(this.assets[0].getPivotX(), this.assets[0].getPivotY());
    }

    public int getPivotX() {
        return this.assets[0].getPivotX();
    }

    public int getPivotY() {
        return this.assets[0].getPivotY();
    }

    public static void changeAnimation(GameObject object, String change, VFAnimation.PlayMode mode) {
        SpriteComponent sprite = object.getComponent(SpriteComponent.class);
        if (sprite != null && sprite.isAnimation(change) == false) {
            sprite.setAnimation(change, mode);
        }
    }
}
