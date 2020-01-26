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
        elapsed = 1/elapsed;

        this.fpsPos.setZero();
        camera.getCamera().unproject(this.fpsPos);
        font.draw(batch, "fps:" + (int)elapsed, this.fpsPos.x, this.fpsPos.y - this.font.getLineHeight());
    }
}
