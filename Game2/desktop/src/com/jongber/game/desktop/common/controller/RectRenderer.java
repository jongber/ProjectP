package com.jongber.game.desktop.common.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.controller.Controller;
import com.jongber.game.core.graphics.OrthoCameraWrapper;
import com.jongber.game.desktop.common.component.RectComponent;

import java.util.List;

public class RectRenderer extends Controller implements Controller.Renderer {

    private List<GameObject> objs;
    private ShapeRenderer renderer = new ShapeRenderer();

    @Override
    public void render(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed) {
        batch.end();

        for (GameObject obj : objs) {
            RectComponent c = obj.getComponent(RectComponent.class);
            Vector2 pos = obj.worldPos();

            Gdx.gl.glLineWidth(c.lineWidth);
            renderer.setProjectionMatrix(camera.getCamera().combined);
            renderer.begin(c.type);
            renderer.setColor(c.color);
            renderer.rect(pos.x + c.rect.x, pos.y + c.rect.y, c.rect.width, c.rect.height);
            renderer.end();
        }

        batch.begin();
    }

    @Override
    public void build(List<GameObject> graph) {
        this.objs = this.buildSimple(graph, RectComponent.class);
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
