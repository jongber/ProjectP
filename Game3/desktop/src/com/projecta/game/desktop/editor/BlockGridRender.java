package com.projecta.game.desktop.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.projecta.game.core.base.pipeline.GamePipeline;

public class BlockGridRender extends GamePipeline implements GamePipeline.InputProcessor, GamePipeline.Renderer {

    private Viewport viewport;
    private OrthographicCamera camera;
    private ShaderProgram shader;
    private Mesh mesh;
    private Matrix4 worldTransform = new Matrix4();

    private MouseState mouse = new MouseState();

    private float lineWidth = 1.0f;
    private Color axisColor = Color.LIGHT_GRAY;
    private Color gridColor = Color.DARK_GRAY;
    private Color backgroundColor = Color.GRAY;
    private float gridSize = 100.0f;

    public BlockGridRender(float gridSize, float lineWidth, Color axisColor, Color gridColor, Color backgroundColor) {
        this();
        this.gridColor = gridColor;
        this.lineWidth = lineWidth;
        this.gridSize = gridSize;
        this.axisColor = axisColor;
        this.backgroundColor = backgroundColor;
    }

    public BlockGridRender() {

        this.createShader();
        this.createQuad();

        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), this.camera);
        this.viewport.apply();

        this.worldTransform.idt();
    }

    @Override
    public void resize(int w, int h) {
        this.viewport.update(w, h);
        this.camera.update();

        this.worldTransform.idt();
    }

    @Override
    public void render(float elapsed) {

        this.shader.bind();
        this.shader.setUniformf("u_axisColor", this.axisColor);
        this.shader.setUniformf("u_gridColor", this.gridColor);
        this.shader.setUniformf("u_backgroundColor", this.backgroundColor);
        this.shader.setUniformf("u_lineWidth", this.lineWidth * this.camera.zoom);
        this.shader.setUniformf("u_gridSize", this.gridSize);

        this.shader.setUniformMatrix("u_projTrans", camera.combined);
        this.shader.setUniformMatrix("u_worldTrans", this.worldTransform);

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
        this.mouse.button.touchDown = true;
        this.mouse.button.x = screenX;
        this.mouse.button.y = screenY;
        this.mouse.button.button = button;

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        this.mouse.button.touchDown = false;

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        MouseState.ButtonState button = this.mouse.button;

        if (button.touchDown) {
            int diffX = button.x - screenX;
            int diffY = button.y - screenY;

            Vector3 translate = new Vector3(diffX, -diffY, 0.0f);
            this.worldTransform.translate(translate);
            this.camera.translate(translate);
            this.camera.update();

            button.x = screenX;
            button.y = screenY;
        }

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {

        float zoomAmount = amountY * 0.02f;

        if (this.camera.zoom + zoomAmount < 0.3f || this.camera.zoom + zoomAmount> 1.8f) {
            return false;
        }

        this.camera.zoom += zoomAmount;
        this.camera.update();

        return false;
    }

    private void createQuad() {
        Quad3D quad = Quad3D.createQuad(false, 10000.0f);

        this.mesh = new Mesh(Mesh.VertexDataType.VertexArray, true, Quad3D.MAX_VERTICES, Quad3D.MAX_INDICES,
                new VertexAttribute(VertexAttributes.Usage.Position, Quad3D.POSITION_COMPONENT, "a_position"));
        this.mesh.setVertices(quad.vert);
        this.mesh.setIndices(quad.indices);
    }

    private void createShader() {
        String vShader = Gdx.files.internal("shader/blockgrid.vert").readString();
        String fShader = Gdx.files.internal("shader/blockgrid.frag").readString();
        this.shader = new ShaderProgram(vShader, fShader);
        if (this.shader.isCompiled() == false) {
            Gdx.app.log("ERROR", this.shader.getLog());
        }

        this.shader.pedantic = false;
    }
}



