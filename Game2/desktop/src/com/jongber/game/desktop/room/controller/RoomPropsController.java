package com.jongber.game.desktop.room.controller;

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

import java.util.List;

public class RoomPropsController extends InputControlAdapter implements Controller.PostRenderer {

    List<GameObject> objects;
    private GameObject selected;
    private ShapeRenderer renderer = new ShapeRenderer();
    private Vector2 pressed = new Vector2();

    @Override
    public void build(List<GameObject> graph) {
        this.objects = this.buildSimple(graph, PropProperty.class);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void postRender(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed) {
        if (this.selected != null) {
            batch.end();

            PropProperty p = this.selected.getComponent(PropProperty.class);
            Vector2 pos = this.selected.transform.getWorldPos();
            float x1 = pos.x;
            float y1 = pos.y;

            Gdx.gl.glLineWidth(5f);
            renderer.setProjectionMatrix(camera.getCamera().combined);
            renderer.begin(ShapeRenderer.ShapeType.Line);
            renderer.setColor(Color.RED);
            renderer.rect(x1, y1, p.texture.getRegionWidth(), p.texture.getRegionHeight());
            renderer.end();

            batch.begin();
        }
    }

    @Override
    public boolean touchDown(OrthoCameraWrapper camera, float worldX, float worldY, int pointer, int button) {

        if (button != 0) {
            return false;
        }

        for (GameObject object : this.objects) {
            PropProperty p = object.getComponent(PropProperty.class);
            Vector2 pos = object.transform.getWorldPos();

            float x1 = pos.x, x2 = pos.x + p.texture.getRegionWidth();
            float y1 = pos.y, y2 = pos.y + p.texture.getRegionHeight();

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
}









