package com.jongber.projectp.object.component;

import com.jongber.projectp.asset.StaticTextureAsset;

public class SceneryComponent {
    private StaticTextureAsset asset;
    private float moveRatio;

    public void setSceneryImage(StaticTextureAsset asset, float moveRatio) {
        this.asset = asset;
        this.moveRatio = moveRatio;
    }

    public StaticTextureAsset getAsset() {
        return this.asset;
    }
}
