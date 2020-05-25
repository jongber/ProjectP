package com.jongber.game.core.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;
import java.util.Map;

public class FontAsset {

    private String fontPath;
    private FreeTypeFontGenerator generator;
    private Map<Integer, BitmapFont> fonts = new HashMap<>();

    public FontAsset(String fontPath) {
        this.fontPath = fontPath;
        this.generator = new FreeTypeFontGenerator(Gdx.files.internal(this.fontPath));
    }

    public FontAsset() {
        this("fonts/DWImpactamin.ttf");
    }

    public void build(String chars, int... fontSizes) {
        for (int size : fontSizes) {
            FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
            param.size = size;
            param.characters = chars;
            BitmapFont font = this.generator.generateFont(param);
            this.fonts.put(size, font);
        }
    }

    public BitmapFont getFont(int size) {
        return this.fonts.get(size);
    }

    public void dispose() {
        for (BitmapFont font : this.fonts.values()) {
            font.dispose();
        }
    }
}
