package com.jongber.projectp;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;
	private Sprite sprite;
	Texture img;
	BitmapFont font;
	private OrthographicCamera camera;
	Viewport viewport;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("stage1/stg1_ground.png");
		img.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
		font = new BitmapFont();
		sprite = new Sprite(img);
		sprite.setScale(3);
		sprite.setOrigin(0, 0);
		camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		viewport = new FillViewport(1280,720,camera);
		viewport.apply();
		//camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);

		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//sprite.translateX(1f);

		camera.update();

		Gdx.app.log("Camera", "" + camera.position);

		camera.translate(0.1f, 0.1f);
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		sprite.draw(batch);
		//batch.draw(img,0,0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		font.dispose();
	}

	@Override
	public void resize(int width, int height){
//		viewport.update(width, height);
//		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
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
