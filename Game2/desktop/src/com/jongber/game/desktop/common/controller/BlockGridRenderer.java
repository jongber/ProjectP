package com.jongber.game.desktop.common.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jongber.game.projectrooms.MainLayer;
import com.jongber.game.core.controller.Controller;
import com.jongber.game.core.graphics.OrthoCameraWrapper;

public class BlockGridRenderer extends Controller implements Controller.PostRenderer {

    private final int BlockSize = 16 * MainLayer.defaltScale;
    private final ShapeRenderer renderer = new ShapeRenderer();

    private final int gridX;
    private final int gridY;

    public boolean showGrid = true;

    public BlockGridRenderer() {
        this.gridY = Gdx.graphics.getWidth() * 3 / BlockSize * BlockSize;
        this.gridX = Gdx.graphics.getHeight() * 3 / BlockSize * BlockSize;
    }

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
        renderer.setColor(Color.DARK_GRAY);
        for (int i = -this.gridX; i < this.gridX; i += BlockSize) {
            renderer.line(i, -this.gridY, i, this.gridY);
        }

        for (int i = -this.gridY; i < this.gridY; i += BlockSize) {
            renderer.line(-this.gridX, i, this.gridX, i);
        }


        renderer.setColor(Color.LIGHT_GRAY);
        renderer.line(0, -this.gridY,0, this.gridY);
        renderer.line(-this.gridX, 0, this.gridX, 0);

        renderer.end();

        batch.begin();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
