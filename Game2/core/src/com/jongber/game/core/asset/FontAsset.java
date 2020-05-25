package com.jongber.game.core.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;
import java.util.Map;

public class FontAsset {

    private FreeTypeFontGenerator generator;
    private Map<Integer, BitmapFont> fonts = new HashMap<>();

    public FontAsset(FileHandle fontFile) {
        this.generator = new FreeTypeFontGenerator(fontFile);
    }

    public void build(String charset, int... fontSizes) {
        for (int size : fontSizes) {

            BitmapFont prev = this.fonts.remove(size);
            if (prev != null)
                prev.dispose();

            FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
            param.size = size;
            param.characters = charset;
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
