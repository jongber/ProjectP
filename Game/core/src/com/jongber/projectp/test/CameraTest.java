package com.jongber.projectp.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jongber.projectp.asset.AssetLoader;
import com.jongber.projectp.asset.SpriteAsset;
import com.jongber.projectp.asset.aseprite.AsepriteJson;
import com.jongber.projectp.graphics.VFAnimation;

public class CameraTest extends ApplicationAdapter {
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;

    private SpriteAsset asset;
    private VFAnimation animation;

    private float ratio;
    private Vector3 cameraPos = new Vector3();

    @Override
    public void create () {
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        viewport = new FillViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),camera);
        viewport.apply();

        AsepriteJson json = AsepriteJson.load("object/hero.json");
        this.asset = AssetLoader.loadSprite("hero", json);
        this.animation = new VFAnimation(this.asset.getAnimation("Attack1"), VFAnimation.PlayMode.LOOP);

        int height = Gdx.graphics.getHeight();
        this.cameraPos.z = this.ratio = height / (64 * 3);

    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.app.log("DEBUG", "camera[" + cameraPos + "]");
        this.camera.position.x = cameraPos.x;
        this.camera.position.y = cameraPos.y;
        this.camera.zoom = 1/cameraPos.z;

        if (this.cameraPos.z > 1.0f) {
            this.cameraPos.z -= 0.01f;
        }

        this.camera.update();

        this.batch.setProjectionMatrix(camera.combined);
        this.batch.begin();

        TextureRegion region = this.animation.getNext(Gdx.graphics.getDeltaTime());
        batch.draw(region, 0, 0);

        this.batch.end();
    }

    @Override
    public void dispose() {
        this.batch.dispose();
    }
}
