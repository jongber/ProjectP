package com.jongber.projectp.graphics;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jongber.projectp.asset.AnimationAsset;

//// Variable Frame Animation..
public class VFAnimation {

    public enum PlayMode {
        LOOP,
        ONCE
    }

    private PlayMode mode;
    private AnimationAsset asset;
    private float elapsed = 0;
    private int curIndex = 0;

    public VFAnimation(AnimationAsset asset, PlayMode mode) {
        this.mode = mode;
        this.asset = asset;
    }

    public TextureRegion getNext(float elapsed) {
        this.elapsed += elapsed;

        int curDuration = this.asset.getFrameDuration(this.curIndex);
        if ((this.elapsed * 1000) > curDuration) {
            this.elapsed -= (float)curDuration / 1000.0f;
            this.curIndex++;
        }

        if (this.curIndex >= this.asset.getFrameLength()) {
            if (this.mode == PlayMode.LOOP)
                this.curIndex = 0;
            else if (this.mode == PlayMode.ONCE)
                this.curIndex --;
        }

        return this.asset.getTextureRegion(curIndex);
    }
}
