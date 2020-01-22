package com.jongber.game.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.jongber.game.editor.StringTable;

import java.util.HashMap;
import java.util.Map;

public class FontManager {

    public enum FontSize{
        Small,          // 10
        Normal,         // 16
        Large,          // 24
        Extra           // 32
    }

    private Map<FontSize, Integer> fontSizeMap = new HashMap<>();
    private Map<FontSize, BitmapFont> fontMap = new HashMap<>();
    private String fontPath = "fonts/Jua-Regular.ttf";
    private FreeTypeFontGenerator generator;

    public void init(String fontPath) {
        this.fontPath = fontPath;
        this.init();
    }

    public void init() {
        this.generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        this.fontSizeMap.put(FontSize.Small, 10);
        this.fontSizeMap.put(FontSize.Normal, 16);
        this.fontSizeMap.put(FontSize.Large, 24);
        this.fontSizeMap.put(FontSize.Extra, 32);
        this.build();
    }

    public void build() {
        StringBuilder charsetBuilder = new StringBuilder();
        charsetBuilder.append(FreeTypeFontGenerator.DEFAULT_CHARS);
        charsetBuilder.append(StringTable.mergeStrings());

        String charset = charsetBuilder.toString();

        for (Map.Entry<FontSize, Integer> pair : fontSizeMap.entrySet()) {
            FreeTypeFontParameter parameter = new FreeTypeFontParameter();
            parameter.size = pair.getValue();
            parameter.characters = charset;

            BitmapFont font = generator.generateFont(parameter);
            this.fontMap.put(pair.getKey(), font);
        }

        generator.dispose();
    }

    public BitmapFont getFont(FontSize size) {
        return this.fontMap.get(size);
    }

    public void dispose() {
        for (Map.Entry<FontSize, BitmapFont> pair : fontMap.entrySet()) {
            pair.getValue().dispose();
        }
    }
}
