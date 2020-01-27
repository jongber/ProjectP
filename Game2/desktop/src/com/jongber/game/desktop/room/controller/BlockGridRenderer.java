package com.jongber.game.desktop.room.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.controller.Controller;
import com.jongber.game.core.graphics.OrthoCameraWrapper;
import java.util.List;

public class BlockGridRenderer extends Controller implements Controller.PostRenderer {

    private final int BlockSize = 16;
    private ShapeRenderer renderer = new ShapeRenderer();

    private int gridX;
    private int gridY;

    public BlockGridRenderer(int screenW, int screenH) {
        this.gridY = screenH * 3 / BlockSize * BlockSize;
        this.gridX = screenW * 3 / BlockSize * BlockSize;
    }

    @Override
    public void postRender(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed) {

        batch.end();

        Vector3 camPos = camera.getPosition(new Vector3());
        int cameraGridX = (int)(camPos.x / BlockSize) * BlockSize;
        int cameraGridY = (int)(camPos.y / BlockSize) * BlockSize;

        Gdx.gl.glLineWidth(0.5f);
        renderer.setProjectionMatrix(camera.getCamera().projection);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.RED);

        for (int i = cameraGridX - this.gridX; i < cameraGridX + this.gridX; i += BlockSize) {
            renderer.line(i, -this.gridY, i, this.gridY);
        }

        for (int i = cameraGridY - this.gridY; i < cameraGridY + this.gridY; i += BlockSize) {
            renderer.line(-this.gridX, i, this.gridX, i);
        }

        //renderer.line(new Vector2(x1, y1), new Vector2(x1, y2));
        renderer.end();
        //Gdx.gl.glLineWidth(1);

        batch.begin();
    }

    @Override
    public void build(List<GameObject> graph) {
    }

    @Override
    public void dispose() {
    }
}
