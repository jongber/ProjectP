package com.jongber.game.core.graphics;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jongber.game.core.asset.AnimationAsset;

//// Variable Frame Animation..
public class VFAnimation {

    public enum PlayMode {
        LOOP,
        ONCE
    }

    private PlayMode mode;
    private AnimationAsset asset;
    private int elapsed = 0;
    private int curIndex = 0;
    private int playbackCount = 0;

    public VFAnimation(AnimationAsset asset, PlayMode mode) {
        init(asset, mode);
    }

    public void init(AnimationAsset asset, PlayMode mode) {
        this.mode = mode;
        this.asset = asset;
        this.elapsed = 0;
        this.curIndex = 0;
        this.playbackCount = 0;
    }

    public String getName() {
        return this.asset.getName();
    }

    public boolean canPlay() {
        return this.asset != null;
    }

    public TextureRegion getNext(float elapsed) {
        this.elapsed += elapsed * 1000.0f;

        int curDuration = this.asset.getFrameDuration(this.curIndex);
        while (this.elapsed > curDuration) {
            this.elapsed -= curDuration;
            this.curIndex++;

            if (this.curIndex >= this.asset.getFrameLength()) {
                if (this.mode == PlayMode.LOOP) {
                    this.curIndex = 0;
                    this.playbackCount++;
                }
                else if (this.mode == PlayMode.ONCE) {
                    this.curIndex--;
                    this.playbackCount = 1;
                }
            }

            curDuration = this.asset.getFrameDuration(this.curIndex);
        }

        return this.asset.getTextureRegion(curIndex);
    }

    public PlayMode getMode() {
        return this.mode;
    }

    public int getPlaybackCount() {
        return this.playbackCount;
    }
}
