package com.jongber.game.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

import java.util.HashSet;
import java.util.Set;

public class FontManager {
    private String fontPath = "fonts/Jua-Regular.ttf";
    private FreeTypeFontGenerator generator;

    private final Set<Character> charset = new HashSet<>();

    public FontManager() {
        this.generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
    }

    public BitmapFont getFont(FreeTypeFontParameter parameter) {
        return this.generator.generateFont(parameter);
    }

    public void dispose() {
        this.generator.dispose();
    }
}
