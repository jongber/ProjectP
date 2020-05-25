package com.jongber.game.projectrooms.controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.controller.Controller;
import com.jongber.game.core.graphics.OrthoCameraWrapper;
import com.jongber.game.projectrooms.MainLayer;
import com.jongber.game.projectrooms.component.DialogComponent;

import java.util.List;


public class DialogController extends Controller implements Controller.GraphBuilder, Controller.Renderer {

    List<GameObject> objects;

    @Override
    public void build(List<GameObject> graph) {
        objects = buildSimple(graph, DialogComponent.class);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void render(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed) {

        BitmapFont font = MainLayer.assets.getFont();

        for (GameObject obj : objects) {
            DialogComponent c = obj.getComponent(DialogComponent.class);
            font.draw(batch, c.layout, 0, 0);
        }
    }
}
