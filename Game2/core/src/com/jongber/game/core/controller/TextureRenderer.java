package com.jongber.game.core.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.jongber.game.core.Controller;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.component.TextureComponent;
import com.jongber.game.core.graphics.OrthoCameraWrapper;

import java.util.ArrayList;
import java.util.List;

public class TextureRenderer extends Controller implements GameLayer.Renderer {

    private List<GameObject> list = new ArrayList<>();

    @Override
    public void build(List<GameObject> graph) {
        this.list.clear();

        for (GameObject node : graph) {
            TextureComponent component = node.getComponent(TextureComponent.class);
             if (component != null) {
                 list.add(node);
             }
        }
    }

    @Override
    public void render(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed) {
        for (GameObject object : list) {

            TextureComponent component = object.getComponent(TextureComponent.class);
            if (component.region == null) {
                Gdx.app.error("TextureRenderer","component region is null " + object.name);
                continue;
            }

            Vector2 translate = new Vector2();
            Vector2 scale = new Vector2();
            float degree = 0.0f;

            Matrix3 world = object.transform.world;
            degree = world.getRotation();
            world.getScale(scale);
            world.getTranslation(translate);

            batch.draw(component.region,
                    translate.x, translate.y,
                    component.pivot.x, component.pivot.y,
                    component.region.getRegionWidth(), component.region.getRegionHeight(),
                    scale.x, scale.y,
                    degree);
        }
    }
}
