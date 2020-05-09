package com.jongber.game.desktop.common.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.controller.Controller;
import com.jongber.game.core.controller.adapter.InputControlAdapter;
import com.jongber.game.core.graphics.OrthoCameraWrapper;
import com.jongber.game.desktop.common.component.SpriteComponent;

import java.util.ArrayList;
import java.util.List;

public class SpritePivotController extends InputControlAdapter implements Controller.Renderer, Controller.PostRenderer {

    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private GameObject selected;
    private List<GameObject> objects = new ArrayList<>();
    private Vector2 pressed = new Vector2();

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

    @Override
    public void build(List<GameObject> graph) {
        this.objects = this.buildSimple(graph, SpriteComponent.class);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void postRender(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed) {
        batch.end();

        Gdx.gl.glLineWidth(3f);
        shapeRenderer.setProjectionMatrix(camera.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);

        for (GameObject object : this.objects) {
            SpriteComponent c = object.getComponent(SpriteComponent.class);
            Vector2 pos = object.worldPos();

            if (c.animation != null && c.animation.canPlay()) {
                Vector2 pivot = c.asset.getPivot();

                TextureRegion region = c.animation.getCurrent();
                shapeRenderer.rect(pos.x + pivot.x, pos.y + pivot.y, region.getRegionWidth(), region.getRegionHeight());
            }
        }

        shapeRenderer.end();

        batch.begin();
    }

    @Override
    public boolean touchDown(OrthoCameraWrapper camera, float worldX, float worldY, int pointer, int button) {
        if (button != 0 || this.objects == null || this.objects.size() == 0) {
            return false;
        }

        this.pressed.set(worldX, worldY);
        if (this.objects.size() != 0) {
            this.selected = this.objects.get(0);
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

        SpriteComponent c = this.selected.getComponent(SpriteComponent.class);
        if (c.animation.canPlay())
            c.asset.getPivot().add(drag);

        return false;
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
