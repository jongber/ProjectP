package com.jongber.projectp.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FillViewport;

public class TestSpriteAnim extends ApplicationAdapter {

    SpriteBatch batch;
    Texture img;
    TextureRegion[] animationFrames;
    Animation animation;
    private OrthographicCamera camera;
    float elapsedTime;

    private Vector2 pos;

    @Override
    public void create () {
        batch = new SpriteBatch();
        img = new Texture("object/hero.png");
        TextureRegion[][] tmpFrames = TextureRegion.split(img,64,64);

        animationFrames = new TextureRegion[4];
        int index = 0;

        for (int i = 0; i < 2; i++){
            for(int j = 0; j < 2; j++) {
                animationFrames[index++] = tmpFrames[j][i];
            }
        }

        animation = new Animation(1f/4f, animationFrames);

        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera.zoom = 0.3f;

        pos = new Vector2();
    }

    @Override
    public void render () {
        elapsedTime += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.position.x -= 0.1f;
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime,true), pos.x, pos.y);
        batch.end();

        pos.x -= 0.1f;
        pos.y -= 0.1f;
    }

    @Override
    public void dispose () {
        this.img.dispose();
        this.batch.dispose();
    }
}
