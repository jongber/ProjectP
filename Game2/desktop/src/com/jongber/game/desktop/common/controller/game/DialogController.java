package com.jongber.game.desktop.common.controller.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.asset.FontManager;
import com.jongber.game.core.controller.Controller;
import com.jongber.game.core.graphics.OrthoCameraWrapper;
import com.jongber.game.desktop.common.component.game.DialogComponent;

import java.util.ArrayList;
import java.util.List;

public class DialogController extends Controller implements Controller.GraphBuilder, Controller.Renderer, Controller.Updater {

    FontManager fontManager = new FontManager();
    List<GameObject> objects;

    @Override
    public void dispose() {
        fontManager.dispose();
    }

    @Override
    public void build(List<GameObject> graph) {
        this.objects = buildSimple(graph, DialogComponent.class);

        List<FontManager.TextBlock> blocks = new ArrayList<>();
        for (GameObject obj : this.objects) {
            DialogComponent comp = obj.getComponent(DialogComponent.class);
            blocks.add(comp.textBlock);
        }

        this.fontManager.build(blocks);
    }

    @Override
    public void update(float elapsed) {

    }

    @Override
    public void render(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed) {
        for (GameObject object : objects) {
            DialogComponent c = object.getComponent(DialogComponent.class);

            BitmapFont font = this.fontManager.getFont(c.textBlock.fontSize);
            GlyphLayout layout = new GlyphLayout(font, c.textBlock.text, c.textBlock.color, 0, Align.left, true);

            Vector2 pos = object.transform.getWorldPos();

            font.draw(batch, layout, pos.x, pos.y);
        }
    }
}
