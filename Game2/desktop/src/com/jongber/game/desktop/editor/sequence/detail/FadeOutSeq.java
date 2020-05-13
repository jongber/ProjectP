package com.jongber.game.desktop.editor.sequence.detail;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.controller.Controller;
import com.jongber.game.core.graphics.OrthoCameraWrapper;
import com.jongber.game.core.sequence.GameSequence;

public class FadeOutSeq extends GameSequence implements Controller.PostRenderer {

    private final float time;
    private Color color = new Color();
    private float totElapsed;
    private ShapeRenderer renderer = new ShapeRenderer();

    public FadeOutSeq(float time) {
        this.time = time;
        this.color.set(0.0f, 0.0f, 0.0f, 0.0f);
    }

    @Override
    public void postRender(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed) {
        batch.end();

        OrthographicCamera c = camera.getCamera();

        Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
        Gdx.gl.glBlendFunc(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
        renderer.setProjectionMatrix(c.combined);
        renderer.setColor(this.color);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        //renderer.rect();

        float w = Gdx.graphics.getWidth() * c.zoom;
        float h = Gdx.graphics.getHeight() * c.zoom;

        renderer.rect(c.position.x - w, c.position.y - h, w * 2, h * 2);
        renderer.end();
        Gdx.gl.glDisable(Gdx.gl.GL_BLEND);

        batch.begin();
    }

    @Override
    public void create(GameLayer layer) {

    }

    @Override
    public void start() {

    }

    @Override
    public void end() {

    }

    @Override
    public boolean isEnded() {
        if (this.totElapsed >= this.time) {
            color.a = 1.0f;
            return true;
        }
        return false;
    }

    @Override
    public void update(float elapsed) {
        this.totElapsed += elapsed;

        this.color.a += elapsed/this.time;
        if (this.color.a >= 1.0f) {
            this.color.a = 1.0f;
        }
    }

    @Override
    public void dispose() {
        this.renderer.dispose();
    }
}
