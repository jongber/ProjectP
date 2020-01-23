package com.jongber.game.editor.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.jongber.game.common.FontManager;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.controller.Controller;
import com.jongber.game.core.graphics.OrthoCameraWrapper;
import com.jongber.game.editor.component.TextComponent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TextRenderer extends Controller implements Controller.Renderer {

    private FontManager fontManager;
    private BitmapFont font;
    private List<GameObject> list = new ArrayList<>();

    public TextRenderer() {
        this.fontManager = new FontManager();
    }

    @Override
    public void render(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed) {

        for (GameObject obj : this.list) {
            TextComponent label = obj.getComponent(TextComponent.class);
            if (label == null) {
                Gdx.app.error("LabelRenderer", "why null? " + obj.name);
            }

            this.font.draw(batch, label.text, 0 ,0);
//            BitmapFont font = this.fontManager.getFont(label.size);
//            font.draw(batch, label.text, 0, 0);
        }

    }

    @Override
    public void build(List<GameObject> graph) {

        this.list.clear();

        if (this.font != null) {
            this.font.dispose();
        }

        StringBuilder builder = new StringBuilder();

        for (GameObject obj : graph) {
            TextComponent label = obj.getComponent(TextComponent.class);
            if (label == null) {
                continue;
            }

            this.list.add(obj);
            builder.append(label.text);
        }

        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = TextComponent.fontSize;
        param.characters = builder.toString();

        this.font = this.fontManager.getFont(param);

    }

    @Override
    public void dispose() {
        if (this.font != null)
            this.font.dispose();

        this.fontManager.dispose();
    }
}
