package com.jongber.game.desktop.common.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.core.controller.Controller;
import com.jongber.game.core.graphics.OrthoCameraWrapper;
import com.jongber.game.desktop.common.component.RoomProperty;
import com.jongber.game.projectz.Const;

import java.util.ArrayList;
import java.util.List;

public class RoomPropertyRenderer extends Controller implements Controller.Renderer, Controller.PostRenderer {

    private List<GameObject> objs = new ArrayList<>();

    @Override
    public void render(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed) {
        for (GameObject object : this.objs) {
            RoomProperty property = object.getComponent(RoomProperty.class);
            if (property == null) {
                Gdx.app.error("RoomPropertyRenderer", "RoomProperty is null");
                continue;
            }

            // render texture
            Vector2 pos = object.transform.getWorldPos();
            batch.draw(property.textureRegion, pos.x, pos.y, property.width, property.height);
        }
    }

    @Override
    public void postRender(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed) {

        Texture left = AssetManager.getTexture("projectz/house/wall/left_wall.png");
        Texture right = AssetManager.getTexture("projectz/house/wall/right_wall.png");
        Texture ceil = AssetManager.getTexture("projectz/house/wall/ceil_wall.png");

        for (GameObject object : this.objs) {
            RoomProperty property = object.getComponent(RoomProperty.class);

            int w = property.width / Const.BlockSize;
            int h = property.height / Const.BlockSize;
            Vector2 pos = object.transform.getWorldPos();

            for (int i = 0; i < h; i++) {
                batch.draw(left, pos.x, pos.y + i * Const.BlockSize);
                batch.draw(right, pos.x + property.width - Const.BlockSize, pos.y + i * Const.BlockSize);
            }

            for (int i = 0; i < w; i++) {
                batch.draw(ceil, pos.x + i * Const.BlockSize, pos.y + property.height - Const.BlockSize);
            }
        }
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
