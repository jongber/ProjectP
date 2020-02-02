package com.jongber.game.core.graphics;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class OrthoCameraWrapper {
    private OrthographicCamera camera = new OrthographicCamera();
    private Viewport viewport;

    public OrthoCameraWrapper(int w, int h) {
        this.viewport = new FitViewport(w, h, this.camera);
        this.viewport.apply();
    }

    public OrthoCameraWrapper(Viewport port) {
        this.viewport = port;
        this.viewport.apply();
    }

    public void update(SpriteBatch batch) {
        this.camera.update();
        batch.setProjectionMatrix(this.camera.combined);
    }

    public void resize(int w, int h) {
        this.viewport.update(w, h);
    }

    public void setPosition(float x, float y) {
        this.camera.position.x = x;
        this.camera.position.y = y;
    }

    public void setPosition(Vector2 pos) {
        this.camera.position.x = pos.x;
        this.camera.position.y = pos.y;
    }

    public void addPosition(Vector2 pos) {
        this.camera.position.x += pos.x;
        this.camera.position.y += pos.y;
    }

    public void setZoom(float zoom) {
        this.camera.zoom = zoom;
    }

    public void addZoom(float zoomDelta) {
        this.camera.zoom += zoomDelta;
    }

    public Vector3 getPosition() {
        //return new Vector2(this.camera.position.x, this.camera.position.y);
        return this.camera.position;
    }

    public Vector3 getPosition(Vector3 pos) {
        pos.set(this.camera.position);
        return pos;
    }

    public float getZoom() {
        return this.camera.zoom;
    }

    public OrthographicCamera getCamera() {
        return this.camera;
    }
}
