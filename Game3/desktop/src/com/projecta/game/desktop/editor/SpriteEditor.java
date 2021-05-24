package com.projecta.game.desktop.editor;

import com.badlogic.gdx.Gdx;
import com.projecta.game.core.GameLayerAppAdapter;
import com.projecta.game.core.util.Tuple2;
import com.projecta.game.desktop.common.panel.HUDPanel;
import com.projecta.game.desktop.editor.panel.SpriteEditorHUDPanel;
import com.projecta.game.desktop.editor.panel.SpriteFramePanel;
import com.projecta.game.desktop.editor.panel.SpriteAnimatePanel;
import com.projecta.game.desktop.editor.panel.TextureRegionPanel;

public class SpriteEditor extends GameLayerAppAdapter {

    private TextureRegionPanel texturePanel;
    private SpriteAnimatePanel animatePanel;
    private SpriteFramePanel framePanel;
    private HUDPanel hud;

    @Override
    public void create() {
        super.create();

        this.texturePanel = new TextureRegionPanel(new Tuple2<>(0.3f, 1.0f), new Tuple2<>(0.0f, 0.0f));
        this.animatePanel = new SpriteAnimatePanel(new Tuple2<>(0.7f, 0.7f), new Tuple2<>(0.3f, 0.3f));
        this.framePanel = new SpriteFramePanel(new Tuple2<>(0.7f, 0.3f), new Tuple2<>(0.3f, 0.0f));
        this.hud = new SpriteEditorHUDPanel(this.framePanel, this.animatePanel, this.texturePanel);

        this.addLayer(this.texturePanel);
        this.addLayer(this.animatePanel);
        this.addLayer(this.framePanel);
        this.addLayer(this.hud);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}








