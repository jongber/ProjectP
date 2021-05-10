package com.projecta.game.desktop.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.projecta.game.core.base.pipeline.GamePipeline;

public class BlockGridRender extends GamePipeline implements GamePipeline.InputProcessor, GamePipeline.Updater {

    private Viewport viewport;
    private OrthographicCamera camera;
    private ShaderProgram shader;
    private Mesh mesh;

    public BlockGridRender() {

        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //this.viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), this.camera);
        //this.viewport.apply();
        String vShader = Gdx.files.internal("shader/blockgrid.vert").readString();
        String fShader = Gdx.files.internal("shader/blockgrid.frag").readString();
        this.shader = new ShaderProgram(vShader, fShader);
        if (this.shader.isCompiled() == false) {
            Gdx.app.log("ERROR", this.shader.getLog());
        }

        Quad3D quad = Quad3D.createQuad(false);

        this.mesh = new Mesh(Mesh.VertexDataType.VertexArray, true, Quad3D.MAX_VERTICES, Quad3D.MAX_INDICES,
                new VertexAttribute(VertexAttributes.Usage.Position, Quad3D.POSITION_COMPONENT, "a_position"));
        this.mesh.setVertices(quad.vert);
        this.mesh.setIndices(quad.indices);
    }

    @Override
    public void update(float elapsed) {
        this.shader.bind();
        this.shader.setUniformMatrix("u_projTrans", camera.combined);
        this.shader.setUniformf("u_camera", camera.position);
        this.mesh.render(shader, GL20.GL_TRIANGLES);
    }

    @Override
    public void dispose() {
        this.mesh.dispose();
        this.shader.dispose();
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
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
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
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}



