package com.projecta.game.desktop.editor.panel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.projecta.game.desktop.common.Env;
import com.projecta.game.desktop.common.panel.HUDPanel;
import com.projecta.game.desktop.common.pipeline.PerfRender;

public class SpriteEditorHUDPanel extends HUDPanel {

    private Skin skin;

    public SpriteEditorHUDPanel() {
        this.init();
    }

    private void init() {
        this.addPipeline(new PerfRender());

        this.skin = new Skin(Gdx.files.internal("skin/holo/uiskin.json"));

        Button button2 = new TextButton("Text Button", this.skin, "colored");
        button2.setPosition(100, 100);

        this.stage.addActor(button2);
    }

    @Override
    public void dispose() {
        super.dispose();
        this.skin.dispose();
    }
}
