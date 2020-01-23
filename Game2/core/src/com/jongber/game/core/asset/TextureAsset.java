package com.jongber.game.core.asset;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class TextureAsset {
    private Texture texture;

    public void set(Texture texture) {
        this.texture = texture;
    }

    public Texture getTexture() {
        return this.texture;
    }

    public void dispose() {
        this.texture.dispose();
    }
}
