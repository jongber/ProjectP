package com.projecta.game.desktop.editor.spriteeditor;

import com.badlogic.gdx.Gdx;
import com.projecta.game.core.GameLayerAppAdapter;
import com.projecta.game.core.util.Tuple2;
import com.projecta.game.desktop.common.Env;
import com.projecta.game.desktop.editor.spriteeditor.cmdwindow.SpriteEditCmd;
import com.projecta.game.desktop.editor.spriteeditor.panel.HUDSpriteEditPanel;
import com.projecta.game.desktop.editor.spriteeditor.panel.SpriteFramePanel;
import com.projecta.game.desktop.editor.spriteeditor.panel.SpriteAnimatePanel;
import com.projecta.game.desktop.editor.spriteeditor.panel.TextureRegionPanel;

public class SpriteEditor extends GameLayerAppAdapter {

    private TextureRegionPanel texturePanel;
    private SpriteAnimatePanel animatePanel;
    private SpriteFramePanel framePanel;
    private HUDSpriteEditPanel hud;

    @Override
    public void create() {
        super.create();

        this.texturePanel = new TextureRegionPanel(new Tuple2<>(0.3f, 1.0f), new Tuple2<>(0.0f, 0.0f));
        this.animatePanel = new SpriteAnimatePanel(new Tuple2<>(0.7f, 0.7f), new Tuple2<>(0.3f, 0.3f));
        this.framePanel = new SpriteFramePanel(new Tuple2<>(0.7f, 0.3f), new Tuple2<>(0.3f, 0.0f));
        this.hud = new HUDSpriteEditPanel(this.framePanel, this.animatePanel, this.texturePanel);

        this.addLayer(this.texturePanel);
        this.addLayer(this.animatePanel);
        this.addLayer(this.framePanel);
        this.addLayer(this.hud);

        Gdx.input.setInputProcessor(this);

        SpriteEditCmd.create(this.hud);
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
        Env.assetManager.dispose();
    }
}








