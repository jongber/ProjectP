package com.jongber.game.editor.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.jongber.game.common.FontManager;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.controller.Controller;
import com.jongber.game.core.graphics.OrthoCameraWrapper;
import com.jongber.game.editor.component.TextComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TextRenderer extends Controller implements Controller.Renderer {

    private FontManager fontManager;
    private Map<Integer, BitmapFont> fonts = new HashMap<>();
    private List<GameObject> list = new ArrayList<>();
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public TextRenderer() {
        this.fontManager = new FontManager();
    }

    @Override
    public void render(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed) {

        batch.end();

        for (GameObject obj : this.list) {
            TextComponent label = obj.getComponent(TextComponent.class);
            if (label == null) {
                Gdx.app.error("LabelRenderer", "why null? " + obj.name);
            }

            BitmapFont font = this.fonts.get(label.fontSize);
            if (font != null) {

                BitmapFont.BitmapFontData data = font.getData();
                int textBoxWidth = 0;
                int textBoxHeight = 0;
                for (int i = 0; i < label.text.length(); ++i) {
                    char character = label.text.charAt(i);
                    BitmapFont.Glyph glyph = data.getGlyph(character);
                    textBoxWidth += glyph.width;
                    if (textBoxHeight < glyph.height)
                        textBoxHeight = glyph.height;
                }

                shapeRenderer.setColor(Color.RED);
                shapeRenderer.setProjectionMatrix(camera.getCamera().projection);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.rect(label.pos.x, label.pos.y - textBoxHeight, textBoxWidth, textBoxHeight);
                shapeRenderer.end();

                batch.begin();
                font.setColor(label.color);
                font.draw(batch, label.text,
                        label.pos.x, label.pos.y,
                        label.width, label.align, false);
                batch.end();
            }
        }

        batch.begin();
    }

    @Override
    public void build(List<GameObject> graph) {

        this.list.clear();
        for (Map.Entry<Integer, BitmapFont> entry : this.fonts.entrySet()) {
            entry.getValue().dispose();
        }
        this.fonts.clear();

        Set<Integer> intSet = new HashSet<>();
        StringBuilder builder = new StringBuilder();

        for (GameObject obj : graph) {
            TextComponent label = obj.getComponent(TextComponent.class);
            if (label == null) {
                continue;
            }

            this.list.add(obj);
            builder.append(label.text);

            intSet.add(label.fontSize);
        }

        builder.append(FreeTypeFontGenerator.DEFAULT_CHARS);

        Iterator<Integer> it = intSet.iterator();
        while (it.hasNext()) {
            int fontSize = it.next();

            FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
            param.size = TextComponent.DefaultSize;
            param.characters = builder.toString();
            BitmapFont font = this.fontManager.getFont(param);
            this.fonts.put(fontSize, font);
        }
    }

    @Override
    public void dispose() {
        for (Map.Entry<Integer, BitmapFont> entry : this.fonts.entrySet()) {
            entry.getValue().dispose();
        }

        this.fontManager.dispose();
    }
}
