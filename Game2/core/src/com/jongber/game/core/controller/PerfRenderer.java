package com.jongber.game.core.controller;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.graphics.OrthoCameraWrapper;

import java.util.List;

public class PerfRenderer extends Controller implements Controller.Renderer{
    private BitmapFont font;
    private Vector3 fpsPos = new Vector3();
    private float totalElapsed = 0.0f;
    private float avgElapsed = 0.016666f;
    private int renderCall = 0;

    public PerfRenderer() {
        font = new BitmapFont();
    }

    @Override
    public void build(List<GameObject> graph) {

    }

    @Override
    public void dispose() {
        this.font.dispose();
    }

    @Override
    public void render(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed) {
        this.totalElapsed += elapsed;
        this.renderCall++;

        if (this.totalElapsed >= 1.0f) {
            this.avgElapsed = this.totalElapsed/this.renderCall;
            this.totalElapsed = 0;
            this.renderCall = 0;
        }

        this.fpsPos.setZero();
        camera.getCamera().unproject(this.fpsPos);
        font.draw(batch, "fps:" + (int)(1/this.avgElapsed), this.fpsPos.x, this.fpsPos.y - this.font.getLineHeight());
    }
}
