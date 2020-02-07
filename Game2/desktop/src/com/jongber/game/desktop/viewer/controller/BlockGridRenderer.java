package com.jongber.game.desktop.viewer.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.controller.Controller;
import com.jongber.game.core.graphics.OrthoCameraWrapper;
import java.util.List;

public class BlockGridRenderer extends Controller implements Controller.PostRenderer {

    private final int BlockSize = 16;
    private final ShapeRenderer renderer = new ShapeRenderer();

    private final int gridX;
    private final int gridY;

    public boolean showGrid = true;

    public BlockGridRenderer(int screenW, int screenH) {
        this.gridY = screenH * 3 / BlockSize * BlockSize;
        this.gridX = screenW * 3 / BlockSize * BlockSize;
    }

    @Override
    public void postRender(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed) {

        if (this.showGrid == false)
            return;

        batch.end();

        Gdx.gl.glLineWidth(1f);
        renderer.setProjectionMatrix(camera.getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.LIGHT_GRAY);
        for (int i = -this.gridX; i < this.gridX; i += BlockSize) {
            renderer.line(i, -this.gridY, i, this.gridY);
        }

        for (int i = -this.gridY; i < this.gridY; i += BlockSize) {
            renderer.line(-this.gridX, i, this.gridX, i);
        }


        renderer.setColor(Color.WHITE);
        renderer.line(0, -this.gridY,0, this.gridY);
        renderer.line(-this.gridX, 0, this.gridX, 0);

        renderer.end();

        batch.begin();
    }

    @Override
    public void build(List<GameObject> graph) {
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
