package com.projecta.game.desktop.common.panel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.projecta.game.core.base.layer.GameLayer;
import com.projecta.game.core.base.layer.GameLayerController;
import com.projecta.game.core.util.Tuple2;
import com.projecta.game.desktop.common.data.MouseState;

public class GamePanel extends GameLayer {

    private Tuple2<Float, Float> screenRatio;
    private Tuple2<Float, Float> posRatio;

    private OrthographicCamera camera;
    private Viewport viewport;

    private MouseState mouseState = new MouseState();

    public GamePanel(Tuple2<Float, Float> screenRatio, Tuple2<Float, Float> posRatio) {
        this.screenRatio = screenRatio;
        this.posRatio = posRatio;

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        this.camera = new OrthographicCamera();
        this.viewport = new ExtendViewport(width * this.screenRatio.getItem1(), height * this.screenRatio.getItem2(), this.camera);
        this.viewport.setScreenBounds((int)(width * this.posRatio.getItem1()), (int)(height * this.posRatio.getItem2()), (int)(width * this.screenRatio.getItem1()), (int)(height * this.screenRatio.getItem2()));
    }

    public Viewport getViewport() {return this.viewport;}

    public OrthographicCamera getCamera() {
        return this.camera;
    }

    @Override
    public void update(float elapsed) {
        this.viewport.apply();

        super.update(elapsed);
    }

    @Override
    public void resize(int width, int height) {

        int screenX = Math.round(width * this.screenRatio.getItem1());
        int screenY = Math.round(height * this.screenRatio.getItem2());

        int posX = Math.round(width * this.posRatio.getItem1());
        int posY = Math.round(height * this.posRatio.getItem2());

        this.viewport.update(screenX, screenY);
        this.viewport.setScreenBounds(posX, posY, screenX, screenY);

        super.resize(width, height);
    }

    @Override
    public boolean keyDown(int keycode) {
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return super.keyUp(keycode);
    }

    @Override
    public boolean keyTyped(char character) {
        return super.keyTyped(character);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if (!this.isInScreenArea(screenX, screenY)) {
            return false;
        }

        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {

        this.mouseState.move.x = screenX;
        this.mouseState.move.y = screenY;

        return super.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {

        if (!this.isInScreenArea(this.mouseState.move.x, this.mouseState.move.y)) {
            return false;
        }

        return super.scrolled(amountX, amountY);
    }

    private boolean isInScreenArea(int screenX, int screenY) {

        boolean yCheck =  screenY < Gdx.graphics.getHeight() - this.viewport.getScreenY()
                && screenY > Gdx.graphics.getHeight() - this.viewport.getScreenY() - this.viewport.getScreenHeight();

        boolean xCheck = screenX > this.viewport.getScreenX()
                && screenX < this.viewport.getScreenWidth() + this.viewport.getScreenX();

        return yCheck && xCheck;
    }
}
