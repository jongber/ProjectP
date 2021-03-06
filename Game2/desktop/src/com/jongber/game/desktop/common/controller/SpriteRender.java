package com.jongber.game.desktop.common.controller;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.jongber.game.projectrooms.MainLayer;
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
        //batch.end();

        for (GameObject obj : objects) {
            SpriteComponent c = obj.getComponent(SpriteComponent.class);
            if (c.animation != null && c.animation.canPlay()) {
                Vector2 p = new Vector2(c.asset.getPivot());
                Vector2 w = obj.worldPos();
                TextureRegion r = c.animation.getNext(elapsed);
                r.flip(c.flipX, c.flipY);

                if (c.flipX) {
                    p.x = r.getRegionWidth() - p.x;
                }

                if (c.flipY) {
                    p.y = r.getRegionWidth() - p.y;
                }

                p.scl(c.scale * MainLayer.defaltScale);

                batch.draw(r, -p.x + w.x, -p.y + w.y, 0.0f, 0.0f, r.getRegionWidth(), r.getRegionHeight(), c.scale * MainLayer.defaltScale, c.scale * MainLayer.defaltScale, c.rotation);
                //batch.draw(r, -p.x, -p.y, w.x, w.y, r.getRegionWidth(), r.getRegionHeight(), c.scale.x, c.scale.y, c.rotation);
                //batch.draw(r, -p.x + w.x, -p.y + w.y);
                //batch.draw(r, w.x, w.y);
                r.flip(c.flipX, c.flipY);
            }
        }

        //batch.begin();
    }
}
