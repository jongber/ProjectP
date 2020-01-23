package com.jongber.game.core.asset;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class AssetManager {
    private static HashMap<String, TextureAsset> textureAssets = new HashMap<>();

    public static TextureAsset getTextureAsset(String filename) {

        if (textureAssets.containsKey(filename)) {
            return textureAssets.get(filename);
        }
        else {
            return loadTexture(filename);
        }
    }

    public static TextureAsset loadTexture(String filename) {
        TextureAsset asset = new TextureAsset();

        Texture texture = new Texture(filename);
        asset.set(texture);

        textureAssets.put(filename, asset);

        return asset;
    }

    public static void dispose() {
        for (TextureAsset asset : textureAssets.values()) {
            asset.dispose();
        }
    }
}
