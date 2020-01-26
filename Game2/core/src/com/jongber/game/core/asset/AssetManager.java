package com.jongber.game.core.asset;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class AssetManager {
    private static HashMap<String, Texture> textureAssets = new HashMap<>();

    public static Texture getTexture(String filename) {

        if (textureAssets.containsKey(filename)) {
            return textureAssets.get(filename);
        }
        else {
            return loadTexture(filename);
        }
    }

    public static Texture loadTexture(String filename) {
        Texture texture = new Texture(filename);

        textureAssets.put(filename, texture);

        return texture;
    }

    public static void dispose() {
        for (Texture asset : textureAssets.values()) {
            asset.dispose();
        }
    }
}
