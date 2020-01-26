package com.jongber.game.core.asset;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.List;

public class AnimationAsset {

    private String name;
    private TextureRegion[] regions;
    private int[] frameDuration;

    public AnimationAsset(String name, TextureRegion[] regions, List<Integer> durations) {
        this.regions = regions;
        this.frameDuration = new int[durations.size()];
        this.name = name;

        for (int i = 0; i < this.frameDuration.length; ++i) {
            this.frameDuration[i] = durations.get(i);
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