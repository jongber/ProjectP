package com.jongber.game.core.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.I18NBundle;
import com.jongber.game.core.util.Tuple2;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;

public class CoreAssetManager {

    // think about hash collision..because key is string..!
    private final HashMap<String, FileHandle> fileHandles = new HashMap<>();
    private final HashMap<String, Texture> textureAssets = new HashMap<>();
    private final HashMap<String, I18NBundle> bundles = new HashMap<>();

    public FileHandle getFile(String path) {

        if (fileHandles.containsKey(path)) {
            return fileHandles.get(path);
        }
        else {
            FileHandle handle = Gdx.files.internal(path);
            fileHandles.put(path, handle);
            return handle;
        }
    }

    public void removeFile(String path) {
        fileHandles.remove(path);
    }

    public Texture getTexture(String filename) {

        if (textureAssets.containsKey(filename)) {
            return textureAssets.get(filename);
        }
        else {
            Texture texture = new Texture(filename);

            textureAssets.put(filename, texture);

            return texture;
        }
    }

    public void removeTexture(String filename) {
        Texture tex = textureAssets.get(filename);
        if (tex != null) {
            tex.dispose();
            textureAssets.remove(filename);
        }
    }

    public I18NBundle getBundle(String filename) {
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

    public String getAllText(String property) {
        StringBuilder builder = new StringBuilder(FreeTypeFontGenerator.DEFAULT_CHARS);

        FileHandle handle = getFile(property + ".properties");

        builder.append(handle.readString());

        try {
            handle = getFile(property + "_" + Locale.getDefault().getLanguage() + ".properties");
            if (handle != null) {
                builder.append(handle.readString());
            }
        }
        catch (GdxRuntimeException e) {
        }

        return mergeText(builder.toString().toCharArray());
    }

    private String mergeText(char[] arr ) {

        HashSet<Character> hashSet = new HashSet<>();

        for (int i = 0; i < arr.length; ++i) {
            if (hashSet.contains(arr[i]) == false) {
                hashSet.add(arr[i]);
            }
        }

        StringBuilder builder = new StringBuilder();

        Iterator<Character> it = hashSet.iterator();
        while (it.hasNext()) {
            builder.append(it.next());
        }

        return builder.toString();
    }

    public void clear() {
        for (Texture asset : textureAssets.values()) {
            asset.dispose();
        }

        fileHandles.clear();
        textureAssets.clear();
        bundles.clear();
    }
}
