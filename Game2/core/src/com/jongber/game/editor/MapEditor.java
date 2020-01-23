package com.jongber.game.editor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.core.controller.TextureRenderer;
import com.jongber.game.editor.component.TextComponent;
import com.jongber.game.editor.controller.TextRenderer;

public class MapEditor extends ApplicationAdapter {
    private SpriteBatch batch;
    private GameLayer mapLayer;
    private GameLayer uiLayer;

    @Override
    public void create () {
        this.batch = new SpriteBatch();
        this.mapLayer = new GameLayer();

        this.createUILayer();
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
        this.uiLayer.registerController(new TextRenderer());
        this.uiLayer.registerController(new TextureRenderer());

        GameObject object = ObjectFactory.createTextBox("안녕구리", new Vector2(0, 0));
        this.uiLayer.addObject(object);
    }
}