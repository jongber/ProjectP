package com.jongber.game.desktop.editor.battle;

import com.badlogic.gdx.Gdx;
import com.jongber.game.core.controller.PerfRenderer;
import com.jongber.game.core.controller.TextureRenderer;
import com.jongber.game.desktop.common.controller.BlockGridRenderer;
import com.jongber.game.desktop.common.controller.CameraController;
import com.jongber.game.desktop.common.controller.RectRenderer;
import com.jongber.game.desktop.common.controller.SpriteRender;
import com.jongber.game.desktop.editor.EditorCmd;
import com.jongber.game.desktop.editor.EditorView;

public class BattleSceneView extends EditorView {

    BattleRule rule;

    @Override
    public void create(EditorCmd cmd) {
        registerController();

        this.rule = new BattleRule(this);

        Gdx.input.setInputProcessor(this.getInput());
    }

    private void registerController() {
        this.registerController(new TextureRenderer());
        this.registerController(new CameraController());
        this.registerController(new RectRenderer());
        this.registerController(new SpriteRender());
        this.registerController(new BlockGridRenderer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }
}
