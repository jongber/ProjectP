package com.jongber.projectp.asset;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jongber.projectp.asset.aseprite.Frame;

import java.util.List;

public class AnimationAsset {
    private String name;
    private TextureRegion[] regions;
    private int[] frameDuration;

    public AnimationAsset(String name, TextureRegion[] regions, List<Frame> list) {
        this.regions = regions;
        this.frameDuration = new int[list.size()];
        this.name = name;

        for (int i = 0; i < this.frameDuration.length; ++i) {
            this.frameDuration[i] = list.get(i).duration;
        }
    }

    public String getName() {
        return this.name;
    }

    public int getFrameLength() {
        return this.regions.length;
    }

    public TextureRegion getTextureRegion(int index) {
        return regions[index];
    }

    public int getFrameDuration(int index) {
        return this.frameDuration[index];
    }
}