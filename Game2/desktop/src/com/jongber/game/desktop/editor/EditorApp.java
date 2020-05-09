package com.jongber.game.desktop.editor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.event.GameEvent;
import com.jongber.game.core.event.GameEventHandler;
import com.jongber.game.desktop.editor.animation.AnimationCmd;

public class EditorApp extends ApplicationAdapter {

    private GameEventHandler handler = new GameEventHandler();
    private GameLayer layer;
    private EditorCmd cmd;

    @Override
    public void create () {
        Inflater.init(this);
        //Inflater.returnToMain();
        Inflater.inflate(AnimationCmd.class);
    }

    @Override
    public void resize (int width, int height) {
        if (layer != null) {
            this.layer.resize(width, height);
        }
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0.45f, 0.45f, 0.45f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handler.handle();

        if (this.layer != null) {
            float elapsed = Gdx.graphics.getDeltaTime();
            layer.update(elapsed);
            layer.render(elapsed);
        }
    }

    @Override
    public void dispose () {
        this.disposeLayer();
        this.disposeCmd();
    }

    public void post(GameEvent e) {
        this.handler.post(e);
    }

    public void setLayer(GameLayer layer) {
        this.layer = layer;
    }

    public void setCmd(EditorCmd cmd) {this.cmd = cmd;}

    private void disposeLayer() {
        if (this.layer != null) {
            this.layer.dispose();
            this.layer = null;
        }
    }

    private void disposeCmd() {
        if (this.cmd != null) {
            this.cmd.dispose();
            this.cmd = null;
        }
    }
}
