package com.jongber.game.desktop.map.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.controller.Controller;
import com.jongber.game.core.controller.adapter.InputControlAdapter;
import com.jongber.game.core.graphics.OrthoCameraWrapper;
import com.jongber.game.desktop.viewer.component.PropProperty;
import com.jongber.game.desktop.viewer.component.RoomProperty;
import com.jongber.game.projectz.Const;

import java.util.List;

public class RoomController extends InputControlAdapter implements Controller.PostRenderer {

    List<GameObject> rooms;
    GameObject selected = null;
    Vector2 pressed = new Vector2();
    ShapeRenderer renderer = new ShapeRenderer();

    @Override
    public void build(List<GameObject> graph) {
        this.rooms = this.buildSimple(graph, RoomProperty.class);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void postRender(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed) {
        if (this.selected != null) {
            batch.end();

            RoomProperty p = this.selected.getComponent(RoomProperty.class);
            Vector2 pos = this.selected.transform.getWorldPos();
            float x1 = pos.x;
            float y1 = pos.y;

            Gdx.gl.glLineWidth(3f);
            renderer.setProjectionMatrix(camera.getCamera().combined);
            renderer.begin(ShapeRenderer.ShapeType.Line);
            renderer.setColor(Color.RED);
            renderer.rect(x1, y1, p.width, p.height);
            renderer.end();

            batch.begin();
        }
    }

    @Override
    public boolean touchDown(OrthoCameraWrapper camera, float worldX, float worldY, int pointer, int button) {
        if (button != 0) {
            return false;
        }

        for (GameObject object : this.rooms) {
            RoomProperty p = object.getComponent(RoomProperty.class);
            Vector2 pos = object.transform.getWorldPos();

            float x1 = pos.x, x2 = pos.x + p.width;
            float y1 = pos.y, y2 = pos.y + p.height;

            if (x1 < worldX && worldX < x2 && y1 < worldY && worldY < y2) {
                this.selected = object;
                this.pressed.set(worldX, worldY);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean touchUp(OrthoCameraWrapper camera, float worldX, float worldY, int pointer, int button) {
        if (this.selected == null)
            return false;

        Vector2 local = this.selected.transform.getLocalPos();
        local.x = Math.round(local.x / Const.BlockSize) * Const.BlockSize;
        local.y = Math.round(local.y / Const.BlockSize) * Const.BlockSize;

        this.selected.transform.local.setToTranslation(local);

        this.selected = null;
        return false;
    }

    @Override
    public boolean touchDragged(OrthoCameraWrapper camera, float worldX, float worldY, int pointer) {
        if (this.selected == null) {
            return false;
        }

        Vector2 drag = new Vector2(worldX, worldY).sub(this.pressed);
        float len = drag.len();
        drag.nor();
        drag.scl(len * 0.8f);

        this.pressed.set(worldX, worldY);

        this.selected.transform.local.translate(drag);

        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }
}
