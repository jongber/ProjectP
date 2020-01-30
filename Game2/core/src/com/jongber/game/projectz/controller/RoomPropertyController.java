package com.jongber.game.projectz.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.controller.Controller;
import com.jongber.game.core.graphics.OrthoCameraWrapper;
import com.jongber.game.projectz.component.RoomProperty;

import java.util.ArrayList;
import java.util.List;

public class RoomPropertyController extends Controller implements Controller.Renderer, Controller.PostRenderer {

    private List<GameObject> objs = new ArrayList<>();

    @Override
    public void render(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed) {
        for (GameObject object : this.objs) {
            RoomProperty property = object.getComponent(RoomProperty.class);
            if (property == null) {
                Gdx.app.error("RoomPropertyController", "RoomProperty is null");
                continue;
            }

            // render texture
            Vector2 pos = object.transform.getPos();
            batch.draw(property.textureRegion, pos.x, pos.y, property.width, property.height);
        }
    }

    @Override
    public void postRender(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed) {
//        for (GameObject object : this.objs) {
//            RoomProperty property = object.getComponent(RoomProperty.class);
//            if (property == null) {
//                Gdx.app.error("RoomPropertyController", "RoomProperty is null");
//                continue;
//            }

//            Vector2 pos = object.transform.getPos();
//            this.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//            this.shapeRenderer.setProjectionMatrix(camera.getCamera().combined);
//
//            this.shapeRenderer.line(pos.x, pos.y, pos.x, pos.y + property.height);
//            this.shapeRenderer.line(pos.x, pos.y + property.height, pos.x + property.width, pos.y + property.height);
//            this.shapeRenderer.line(pos.x + property.width, pos.y, pos.x + property.width, pos.y + property.height);
//            this.shapeRenderer.end();
//        }
    }

    @Override
    public void build(List<GameObject> graph) {
        this.objs.clear();

        for (GameObject node : graph) {
            if (node.hasComponent(RoomProperty.class)) {
                this.objs.add(node);
            }
        }
    }

    @Override
    public void dispose() {

    }
}
