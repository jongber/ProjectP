package com.jongber.game.core.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.List;

public class AnimationAsset {

    private final String name;
    private final TextureRegion[] regions;
    private final int[] frameDuration;

    public AnimationAsset(String name, List<TextureRegion> regions, List<Integer> durations) {
        this(name, (TextureRegion[]) regions.toArray(), durations);
    }

    public AnimationAsset(String name, TextureRegion[] regions, List<Integer> durations) {
        this.regions = regions;
        this.frameDuration = new int[durations.size()];
        this.name = name;

        for (int i = 0; i < this.frameDuration.length; ++i) {
            this.frameDuration[i] = durations.get(i);
        }

        this.log();
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

    private void log() {
        if (this.regions.length != this.frameDuration.length) {
            Gdx.app.error("AnimationAsset", "animation length has problem..");
        }
    }
}