package com.jongber.projectp.oldaction.object.component;

import com.jongber.projectp.oldaction.asset.StaticTextureAsset;

public class SceneryComponent {
    private StaticTextureAsset asset;
    private float moveRatio;

    public SceneryComponent(StaticTextureAsset asset, float moveRatio) {
        this.setSceneryImage(asset, moveRatio);
    }

    public void setSceneryImage(StaticTextureAsset asset, float moveRatio) {
        this.asset = asset;
        this.moveRatio = moveRatio;
    }

    public StaticTextureAsset getAsset() {
        return this.asset;
    }
}
