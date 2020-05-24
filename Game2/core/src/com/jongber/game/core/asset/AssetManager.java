package com.jongber.game.core.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.HashMap;
import java.util.Locale;

public class AssetManager {

    private final static String defaultProperties = "project/strings";
    // think about hash collision..because key is string..!
    private final static HashMap<String, FileHandle> fileHandles = new HashMap<>();
    private final static HashMap<String, Texture> textureAssets = new HashMap<>();
    private final static HashMap<String, I18NBundle> bundles = new HashMap<>();

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
            Texture texture = new Texture(filename);

            textureAssets.put(filename, texture);

            return texture;
        }
    }

    public static void removeTexture(String filename) {
        Texture tex = textureAssets.get(filename);
        if (tex != null) {
            tex.dispose();
            textureAssets.remove(filename);
        }
    }

    public static I18NBundle getBundle() {
        return getBundle(defaultProperties);
    }

    private static I18NBundle getBundle(String filename) {
        if (bundles.containsKey(filename) == false) {
            FileHandle handle = getFile(filename);
            Locale locale = Locale.getDefault();
            I18NBundle myBundle = I18NBundle.createBundle(handle, locale);
            bundles.put(filename, myBundle);

            return myBundle;
        }
        else {
            return bundles.get(filename);
        }
    }

    public static void dispose() {
        for (Texture asset : textureAssets.values()) {
            asset.dispose();
        }

        fileHandles.clear();
        textureAssets.clear();
        bundles.clear();
    }
}
