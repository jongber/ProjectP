package com.projecta.game.desktop.common.panel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.projecta.game.core.base.layer.GameLayer;
import com.projecta.game.core.util.Tuple2;

public class HUDPanel extends GamePanel {

    protected Stage stage;

    public HUDPanel() {
        super(new Tuple2<>(1.0f, 1.0f), new Tuple2<>(0.0f, 0.0f));
        stage = new Stage(this.getViewport());
    }

    public HUDPanel(Tuple2<Float, Float> screenRatio, Tuple2<Float, Float> posRatio) {
        super(screenRatio, posRatio);
        stage = new Stage(this.getViewport());
    }

    @Override
    public void update(float elapsed) {
        Gdx.gl20.glDisable(GL20.GL_DEPTH_TEST);
        super.update(elapsed);

        this.stage.act();
        this.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
        this.stage.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {

        if (this.stage.keyDown(keycode)) {
            return true;
        }

        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {

        if (this.stage.keyDown(keycode)) {
            return true;
        }

        return super.keyUp(keycode);
    }

    @Override
    public boolean keyTyped(char character) {
        if (this.stage.keyTyped(character)) {
            return true;
        }

        return super.keyTyped(character);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (this.stage.touchDown(screenX, screenY, pointer, button)) {
            return true;
        }

        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (this.stage.touchUp(screenX, screenY, pointer, button)) {
            return true;
        }

        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (this.stage.touchDragged(screenX, screenY, pointer)) {
            return true;
        }
        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        if (this.stage.mouseMoved(screenX, screenY)) {
            return true;
        }

        return super.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        if (this.stage.scrolled(amountX, amountY)) {
            return true;
        }

        return super.scrolled(amountX, amountY);
    }
}
