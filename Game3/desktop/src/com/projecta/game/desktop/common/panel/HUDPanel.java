package com.projecta.game.desktop.common.panel;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.projecta.game.core.base.layer.GameLayer;

public class HUDPanel extends GameLayer {

    protected Stage stage = new Stage(new ScreenViewport());

    public HUDPanel() {
    }

    @Override
    public void update(float elapsed) {
        super.update(elapsed);

        this.stage.act();
        this.stage.draw();
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
