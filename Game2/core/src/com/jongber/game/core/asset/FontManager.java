package com.jongber.game.core.asset;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FontManager {
    private String fontPath;
    private FreeTypeFontGenerator generator;
    private Map<Integer, BitmapFont> fonts = new HashMap<>();

    public static class TextBlock {
        static final int DefaultFontSize = 24;
        public String text = "";
        public int fontSize = DefaultFontSize;
        public Color color = Color.WHITE;
    }

    public FontManager(String fontPath) {
        this.fontPath = fontPath;
        this.generator = new FreeTypeFontGenerator(AssetManager.getFile(this.fontPath));
    }

    public FontManager() {
        this("fonts/Jua-Regular.ttf");
    }

    public void build(List<TextBlock> list) {
        this.clear();

        Set<Integer> intSet = new HashSet<>();
        StringBuilder builder = new StringBuilder();

        for (TextBlock block : list) {
            builder.append(block.text);
            intSet.add(block.fontSize);
        }

        builder.append(FreeTypeFontGenerator.DEFAULT_CHARS);

        Iterator<Integer> it = intSet.iterator();
        while (it.hasNext()) {
            int fontSize = it.next();
            FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
            param.size = fontSize;
            param.characters = builder.toString();
            BitmapFont font = this.generator.generateFont(param);
            this.fonts.put(fontSize, font);
        }
    }

    public BitmapFont getFont(int size) {
        return this.fonts.get(size);
    }

    public void dispose() {
        this.clear();
        this.generator.dispose();
        AssetManager.removeFile(this.fontPath);
    }

    private void clear() {
        for (BitmapFont font : this.fonts.values()) {
            font.dispose();
        }

        this.fonts.clear();
    }
}
