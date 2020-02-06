package com.jongber.game.desktop.viewer.controller;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.controller.Controller;
import com.jongber.game.core.graphics.OrthoCameraWrapper;
import com.jongber.game.desktop.viewer.component.PropProperty;
import com.jongber.game.desktop.viewer.component.RoomProperty;

import java.util.List;

public class RoomPropsRenderer extends Controller implements Controller.Renderer {

    List<GameObject> objects;

    @Override
    public void build(List<GameObject> graph) {
        this.objects = this.buildSimple(graph, PropProperty.class);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void render(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed) {
        GameObject parent = null;
        Rectangle scissors = new Rectangle();

        batch.flush();

        for (GameObject object : this.objects) {
            if (parent != object.getParent()) {
                parent = object.getParent();
                Vector2 pPos = parent.transform.getWorldPos();
                RoomProperty r = parent.getComponent(RoomProperty.class);

                Rectangle cliBounds = new Rectangle(pPos.x, pPos.y, r.width, r.height);
                camera.getViewport().calculateScissors(batch.getTransformMatrix(), cliBounds, scissors);
                //ScissorStack.calculateScissors(camera.getCamera(), batch.getTransformMatrix(), cliBounds, scissors);
            }

            if (ScissorStack.pushScissors(scissors)) {
                PropProperty p = object.getComponent(PropProperty.class);
                Vector2 pos = object.transform.getWorldPos();
                batch.draw(p.texture, pos.x, pos.y);
                batch.flush();
                ScissorStack.popScissors();
            }
        }
    }
}
