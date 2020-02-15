package com.jongber.game.desktop.common.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.controller.Controller;
import com.jongber.game.core.graphics.OrthoCameraWrapper;
import com.jongber.game.desktop.common.component.BorderComponent;

import java.util.List;

public class BorderPostRenderer extends Controller implements Controller.PostRenderer {

    private List<GameObject> objects;
    private ShapeRenderer renderer = new ShapeRenderer();

    public BorderPostRenderer() {
    }

    @Override
    public void postRender(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed) {

        batch.end();

        renderer.setProjectionMatrix(camera.getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);

        for (GameObject object : objects) {
            Vector2 pos = object.transform.getWorldPos();
            BorderComponent b = object.getComponent(BorderComponent.class);
            Gdx.gl.glLineWidth(b.lineWidth);
            renderer.setColor(b.color);
            renderer.rect(pos.x, pos.y, b.width, b.height);
            renderer.flush();
        }

        renderer.end();
        Gdx.gl.glLineWidth(1);

        batch.begin();
    }

    @Override
    public void build(List<GameObject> graph) {
        objects = buildSimple(graph, BorderComponent.class);
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
