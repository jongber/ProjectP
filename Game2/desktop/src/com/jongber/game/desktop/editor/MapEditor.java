package com.jongber.game.desktop.editor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.core.controller.TextureRenderer;
import com.jongber.game.core.controller.PerfRenderer;
import com.jongber.game.core.component.TextComponent;
import com.jongber.game.core.controller.TextController;
import com.jongber.game.desktop.editor.event.LoadMapEvent;
import com.jongber.game.desktop.editor.event.NewMapEvent;

public class MapEditor extends ApplicationAdapter implements InputProcessor {
    private SpriteBatch batch;
    private GameLayer mapLayer;
    private GameLayer uiLayer;

    @Override
    public void create () {
        this.batch = new SpriteBatch();
        this.mapLayer = new GameLayer();

        this.createUILayer();

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float elapsed = Gdx.graphics.getDeltaTime();

        batch.begin();
        this.mapLayer.update(elapsed);
        this.uiLayer.update(elapsed);

        this.mapLayer.render(this.batch, elapsed);
        this.uiLayer.render(this.batch, elapsed);
        batch.end();
    }

    @Override
    public void resize (int width, int height) {
        this.mapLayer.resize(width, height);
        this.uiLayer.resize(width, height);
    }

    @Override
    public void dispose() {
        this.batch.dispose();
        this.mapLayer.dispose();
        this.uiLayer.dispose();
        AssetManager.dispose();
    }

    private void createUILayer() {
        this.uiLayer = new GameLayer();
        this.uiLayer.registerController(new TextureRenderer());
        this.uiLayer.registerController(new PerfRenderer());
        this.uiLayer.registerController(new TextController());

        GameObject object = ObjectFactory.createButton("New Map",
                new Vector2(-Gdx.graphics.getWidth()/8, 0), new TextComponent.ClickListener() {
            @Override
            public void onClicked() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MapEditor.this.uiLayer.post(new NewMapEvent(MapEditor.this.uiLayer, MapEditor.this.mapLayer));
                    }
                }).start();

                MapEditor.this.uiLayer.resetObject();
            }
        });
        this.uiLayer.addObject(object);

        object = ObjectFactory.createButton("Load Map",
                new Vector2(Gdx.graphics.getWidth()/8, 0), new TextComponent.ClickListener() {
                    @Override
                    public void onClicked() {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                MapEditor.this.uiLayer.post(new LoadMapEvent());
                            }
                        }).start();

                        MapEditor.this.uiLayer.resetObject();
                    }
                });
        this.uiLayer.addObject(object);

    }

    @Override
    public boolean keyDown(int keycode) {
        if (this.uiLayer.getInput().keyDown(keycode)) {
            this.mapLayer.getInput().keyDown(keycode);
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (this.uiLayer.getInput().keyUp(keycode)) {
            this.mapLayer.getInput().keyUp(keycode);
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (this.uiLayer.getInput().touchDown(screenX, screenY, pointer, button)) {
            this.mapLayer.getInput().touchDown(screenX, screenY, pointer, button);
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (this.uiLayer.getInput().touchUp(screenX, screenY, pointer, button)) {
            this.mapLayer.getInput().touchUp(screenX, screenY, pointer, button);
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (this.uiLayer.getInput().touchDragged(screenX, screenY, pointer)) {
            this.mapLayer.getInput().touchDragged(screenX, screenY, pointer);
        }

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        if (this.uiLayer.getInput().mouseMoved(screenX, screenY)) {
            this.mapLayer.getInput().mouseMoved(screenX, screenY);
        }

        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        if (this.uiLayer.getInput().scrolled(amount)) {
            this.mapLayer.getInput().scrolled(amount);
        }

        return false;
    }
}