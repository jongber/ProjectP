package com.jongber.projectp.test;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jongber.projectp.asset.AssetLoader;
import com.jongber.projectp.asset.SpriteAsset;
import com.jongber.projectp.asset.json.AsepriteJson;
import com.jongber.projectp.graphics.VFAnimation;

public class CameraTest extends ApplicationAdapter implements InputProcessor {
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;

    private SpriteAsset asset;
    private VFAnimation animation = new VFAnimation();

    private float ratio;
    private Vector3 cameraPos = new Vector3();
    private Stage stage;

    private SpriteBatch hudBatch;
    BitmapFont font;

    @Override
    public void create () {
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        this.hudBatch = new SpriteBatch();
        //viewport = new FillViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), camera);
        viewport = new FillViewport(160, 90, camera);
        viewport.apply();

        AsepriteJson json = AsepriteJson.load("object/hero.json");
        this.asset = AssetLoader.loadSprite("hero", json);
        this.animation.init(this.asset.getAnimation("Attack1"), VFAnimation.PlayMode.LOOP);

        int height = Gdx.graphics.getHeight();
        this.cameraPos.z = this.ratio = height / (64 * 3);

        Gdx.input.setInputProcessor(this);

        font = new BitmapFont();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Gdx.app.log("DEBUG", "camera[" + cameraPos + "]");
//        this.camera.position.x = cameraPos.x;
//        this.camera.position.y = cameraPos.y;
//        this.camera.zoom = 1 * cameraPos.z;
//
//        if (this.cameraPos.z > 1.0f) {
//            this.cameraPos.z -= 0.005f;
//        }

        this.camera.update();

        this.batch.setProjectionMatrix(camera.combined);
        this.batch.begin();

        TextureRegion region = this.animation.getNext(Gdx.graphics.getDeltaTime());
        batch.draw(region, 0, 0);

        this.font.draw(batch, "Hello world", 0,0);
        this.batch.end();

        this.hudBatch.begin();
        this.font.draw(hudBatch, "Hello world", 0, 20);
        this.hudBatch.end();
    }

    @Override
    public void dispose() {
        this.batch.dispose();
        this.font.dispose();
        this.hudBatch.dispose();
    }

    @Override
    public void resize(int width, int height){
		viewport.update(width, height);
		//camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Gdx.app.log("Mouse Event","Click at " + screenX + "," + screenY);
        Vector3 worldCoordinates = camera.unproject(new Vector3(screenX,screenY,0));
        Gdx.app.log("Mouse Event","Projected at " + worldCoordinates.x + "," + worldCoordinates.y);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
