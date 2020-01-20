package com.jongber.game.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.jongber.game.editor.StringTable;

public class FontManager {

    private BitmapFont small;   // 10
    private BitmapFont normal;  // 16
    private BitmapFont large;   // 24
    private BitmapFont extra;   // 32

    public void init() {

        StringBuilder charsetBuilder = new StringBuilder();
        charsetBuilder.append(FreeTypeFontGenerator.DEFAULT_CHARS);
        charsetBuilder.append(StringTable.mergeStrings());

        String charset = charsetBuilder.toString();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Jua-Regular.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 10;
        parameter.characters = charset;
        this.small = generator.generateFont(parameter);

        parameter = new FreeTypeFontParameter();
        parameter.size = 16;
        parameter.characters = charset;
        this.normal = generator.generateFont(parameter);

        parameter = new FreeTypeFontParameter();
        parameter.size = 24;
        parameter.characters = charset;
        this.large = generator.generateFont(parameter);

        parameter = new FreeTypeFontParameter();
        parameter.size = 32;
        parameter.characters = charset;
        this.extra = generator.generateFont(parameter);

        generator.dispose();
    }

    public void dispose() {
        this.small.dispose();
        this.normal.dispose();
        this.large.dispose();
        this.extra.dispose();
    }
}
