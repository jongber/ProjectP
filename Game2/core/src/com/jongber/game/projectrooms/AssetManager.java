package com.jongber.game.projectrooms;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.I18NBundle;
import com.jongber.game.core.asset.CoreAssetManager;
import com.jongber.game.core.asset.FontAsset;

import java.util.Map;

public class AssetManager extends CoreAssetManager {

    private final static String defaultProperties = "project/text/strings";
    private final static String defaultFont = "fonts/DWImpactamin.ttf";
    private final static int defaultFontSize = 24;

    private FontAsset fontAsset;

    public AssetManager() {
        init();
    }

    public void init() {
        String allText = getAllText(defaultProperties);
        this.fontAsset = new FontAsset(getFile(defaultFont));
        this.fontAsset.build(allText, defaultFontSize);
    }

    public BitmapFont getFont() {
        return this.fontAsset.getFont(defaultFontSize);
    }

    public I18NBundle getBundle() {
        return getBundle(defaultProperties);
    }

    public void clear() {
        super.clear();
        fontAsset.dispose();
    }
}
