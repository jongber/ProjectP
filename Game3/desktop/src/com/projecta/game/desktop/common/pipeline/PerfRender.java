package com.projecta.game.desktop.common.pipeline;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.projecta.game.core.base.pipeline.GamePipeline;

public class PerfRender extends GamePipeline implements GamePipeline.Renderer {

    private Viewport viewport;
    private OrthographicCamera camera;

    private SpriteBatch batch = new SpriteBatch();

    private BitmapFont font;
    private Vector3 fpsPos = new Vector3();
    private float totalElapsed = 0.0f;
    private float avgElapsed = 0.016666f;
    private int renderCall = 0;

    public PerfRender() {
        font = new BitmapFont();

        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), this.camera);
        this.viewport.apply();
    }

    @Override
    public void resize(int w, int h) {
        this.viewport.update(w, h);
        this.camera.update();
    }

    @Override
    public void render(float elapsed) {
        this.viewport.apply();

        this.batch.setProjectionMatrix(this.camera.combined);
        this.batch.begin();

        this.totalElapsed += elapsed;
        this.renderCall++;

        if (this.totalElapsed >= 1.0f) {
            this.avgElapsed = this.totalElapsed/this.renderCall;
            this.totalElapsed = 0;
            this.renderCall = 0;
        }

        this.fpsPos.setZero();
        this.camera.unproject(this.fpsPos);
        font.draw(batch, "fps:" + (int)(1/this.avgElapsed), this.fpsPos.x, this.fpsPos.y - this.font.getLineHeight());

        this.batch.end();
    }

    @Override
    public void dispose() {
        this.batch.dispose();
        this.font.dispose();
    }
}
