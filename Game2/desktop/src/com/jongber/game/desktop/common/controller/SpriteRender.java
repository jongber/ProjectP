package com.jongber.game.desktop.common.controller;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.controller.Controller;
import com.jongber.game.core.graphics.OrthoCameraWrapper;
import com.jongber.game.desktop.common.component.SpriteComponent;

import java.util.List;

public class SpriteRender extends Controller implements Controller.GraphBuilder, Controller.Renderer {

    List<GameObject> objects;

    @Override
    public void dispose() {

    }

    @Override
    public void build(List<GameObject> graph) {
        objects = this.buildSimple(graph, SpriteComponent.class);
    }

    @Override
    public void render(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed) {
        for (GameObject obj : objects) {
            SpriteComponent c = obj.getComponent(SpriteComponent.class);
            if (c.animation != null && c.animation.canPlay()) {
                Vector2 p = c.asset.getPivot();
                Vector2 w = obj.worldPos();
                TextureRegion r = c.animation.getNext(elapsed);
                batch.draw(r, p.x + w.x, p.y + w.y);
            }
        }
    }
}
