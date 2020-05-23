package com.jongber.game.desktop.editor.battle;

import com.badlogic.gdx.Gdx;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.core.component.TextComponent;
import com.jongber.game.core.controller.PerfRenderer;
import com.jongber.game.core.controller.TextController;
import com.jongber.game.core.controller.TextureRenderer;
import com.jongber.game.desktop.common.controller.BlockGridRenderer;
import com.jongber.game.desktop.common.controller.CameraController;
import com.jongber.game.desktop.common.controller.RectRenderer;
import com.jongber.game.desktop.common.controller.SpriteRender;
import com.jongber.game.desktop.editor.EditorCmd;
import com.jongber.game.desktop.editor.EditorView;

import sun.java2d.pipe.TextRenderer;

public class BattleSceneView extends EditorView {

    BattleRule rule;

    @Override
    public void create(EditorCmd cmd) {
        this.rule = new BattleRule(this);
        registerController();

        ////Gdx.input.setInputProcessor(this.getInput());
        GameObject text = new GameObject();

        String str = AssetManager.getBundle().get("_test_text");

        text.addComponent(new TextComponent(str, null));

        this.addObject(text);
    }

    private void registerController() {
        this.registerController(new TextureRenderer());
        this.registerController(new CameraController());
        this.registerController(new RectRenderer());
        this.registerController(new SpriteRender());
        this.registerController(new BattleController(this, this.rule));
        this.registerController(new BlockGridRenderer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        this.registerController(new TextController());
    }
}
