package com.jongber.game.core.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class AssetManager {

    // think about hash collision..because key is string..!
    private final static HashMap<String, FileHandle> fileHandles = new HashMap<>();
    private final static HashMap<String, Texture> textureAssets = new HashMap<>();

    public static FileHandle getFile(String path) {

        if (fileHandles.containsKey(path)) {
            return fileHandles.get(path);
        }
        else {
            FileHandle handle = Gdx.files.internal(path);
            fileHandles.put(path, handle);
            return handle;
        }
    }

    public static void removeFile(String path) {
        fileHandles.remove(path);
    }

    public static Texture getTexture(String filename) {

        if (textureAssets.containsKey(filename)) {
            return textureAssets.get(filename);
        }
        else {
            return loadTexture(filename);
        }
    }

    public static void removeTexture(String filename) {
        Texture tex = textureAssets.get(filename);
        if (tex != null) {
            tex.dispose();
            textureAssets.remove(filename);
        }
    }

    private static Texture loadTexture(String filename) {
        Texture texture = new Texture(filename);

        textureAssets.put(filename, texture);

        return texture;
    }

    public static void dispose() {
        for (Texture asset : textureAssets.values()) {
            asset.dispose();
        }

        fileHandles.clear();
        textureAssets.clear();
    }
}
