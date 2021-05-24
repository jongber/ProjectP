package com.projecta.game.desktop.editor.panel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.projecta.game.desktop.common.panel.HUDPanel;
import com.projecta.game.desktop.common.pipeline.PerfRender;

public class SpriteEditorHUDPanel extends HUDPanel {

    private Skin skin;

    public SpriteEditorHUDPanel() {
        this.init();
    }

    private void init() {
        this.addPipeline(new PerfRender());

        FileHandle f = Gdx.files.internal("skin/holo/uiskin.json");

        this.skin = new Skin(f);
    }
}
