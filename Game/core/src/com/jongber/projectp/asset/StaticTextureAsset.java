package com.jongber.projectp.asset;

import com.badlogic.gdx.graphics.Texture;

public class StaticTextureAsset {
    private Texture texture;



    public void dispose() {
        this.texture.dispose();
    }
}
