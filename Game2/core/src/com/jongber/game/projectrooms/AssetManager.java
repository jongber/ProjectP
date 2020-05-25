package com.jongber.game.projectrooms;

import com.jongber.game.core.asset.AssetLoader;

public class AssetManager {

    private final static String defaultProperties = "project/text/strings";

    private final static AssetLoader loader = new AssetLoader();

    public void clear() {
        loader.clear();
    }
}
