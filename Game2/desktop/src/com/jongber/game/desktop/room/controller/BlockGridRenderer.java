package com.jongber.game.desktop.room.controller;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.controller.Controller;
import com.jongber.game.core.graphics.OrthoCameraWrapper;
import java.util.List;

public class BlockGridRenderer extends Controller implements Controller.PostRenderer {

    private final int BlockSize = 16;
    private ShapeRenderer renderer = new ShapeRenderer();

    @Override
    public void postRender(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed) {

    }

    @Override
    public void build(List<GameObject> graph) {
    }

    @Override
    public void dispose() {
    }
}
