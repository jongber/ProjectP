package com.jongber.game.desktop.editor.sprite.controller;

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
import com.jongber.game.desktop.editor.sprite.component.AsepriteComponent;

import java.util.List;

public class AsepriteController extends InputControlAdapter implements Controller.Renderer, Controller.PostRenderer {

    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private GameObject selected;
    private List<GameObject> objects;
    private Vector2 pressed = new Vector2();

    @Override
    public void build(List<GameObject> graph) {
        this.objects = this.buildSimple(graph, AsepriteComponent.class);
        if (this.objects.size() != 0) {
            this.selected = this.objects.get(0);
        }
    }

    @Override
    public void render(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed) {
        for (GameObject object : objects) {
            AsepriteComponent c = object.getComponent(AsepriteComponent.class);
            Vector2 pos = object.transform.getWorldPos();
            if (c.currentAnimation != null) {
                AsepriteComponent.AnimData data = c.assetMap.get(c.currentAnimation.getName());
                Vector2 pivot = new Vector2();
                if (data != null) {
                    pivot = data.pivot;
                }
                TextureRegion region = c.currentAnimation.getNext(elapsed);
                batch.draw(region, pos.x + pivot.x, pos.y + pivot.y);
            }
            else
                batch.draw(c.totalImages.get(0), pos.x, pos.y);
        }
    }

    @Override
    public void postRender(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed) {
        batch.end();

        Gdx.gl.glLineWidth(3f);
        shapeRenderer.setProjectionMatrix(camera.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);

        for (GameObject object : this.objects) {
            AsepriteComponent c = object.getComponent(AsepriteComponent.class);
            Vector2 pos = object.transform.getWorldPos();

            if (c.currentAnimation != null) {
                AsepriteComponent.AnimData data = c.assetMap.get(c.currentAnimation.getName());
                Vector2 pivot = new Vector2();
                if (data != null) {
                    pivot = data.pivot;
                }
                TextureRegion region = c.currentAnimation.getCurrent();
                shapeRenderer.rect(pos.x + pivot.x, pos.y + pivot.y, region.getRegionWidth(), region.getRegionHeight());
            }
        }

        shapeRenderer.end();

        batch.begin();
    }

    @Override
    public boolean touchDown(OrthoCameraWrapper camera, float worldX, float worldY, int pointer, int button) {
        if (button != 0) {
            return false;
        }

        this.pressed.set(worldX, worldY);

        return false;
    }

    @Override
    public boolean touchUp(OrthoCameraWrapper camera, float worldX, float worldY, int pointer, int button) {
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

        AsepriteComponent c = this.selected.getComponent(AsepriteComponent.class);
        String name = c.currentAnimation.getName();
        AsepriteComponent.AnimData a = c.assetMap.get(name);
        a.pivot.add(drag);

//        this.selected.transform.local.translate(drag);

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














