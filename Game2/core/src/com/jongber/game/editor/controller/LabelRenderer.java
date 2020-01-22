package com.jongber.game.editor.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jongber.game.common.FontManager;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.controller.Controller;
import com.jongber.game.core.graphics.OrthoCameraWrapper;
import com.jongber.game.editor.component.LabelComponent;

import java.util.ArrayList;
import java.util.List;

public class LabelRenderer extends Controller implements Controller.Renderer {

    private FontManager fontManager;
    private List<GameObject> list = new ArrayList<>();

    public LabelRenderer(FontManager font) {
        this.fontManager = font;
    }

    @Override
    public void render(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed) {

        for (GameObject obj : this.list) {
            LabelComponent label = obj.getComponent(LabelComponent.class);
            if (label == null) {
                Gdx.app.error("LabelRenderer", "why null? " + obj.name);
            }

            BitmapFont font = this.fontManager.getFont(label.size);
            font.draw(batch, label.text, 0, 0);
        }

    }

    @Override
    public void build(List<GameObject> graph) {
        for (GameObject obj : graph) {
            if (obj.hasComponent(LabelComponent.class)) {
                this.list.add(obj);
            }
        }
    }
}
