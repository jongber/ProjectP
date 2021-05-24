package com.projecta.game.desktop.editor.panel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.projecta.game.desktop.common.panel.HUDPanel;
import com.projecta.game.desktop.common.pipeline.PerfRender;

public class SpriteEditorHUDPanel extends HUDPanel {

    private Skin skin;

    private SpriteFramePanel framePanel;
    private SpriteAnimatePanel animatePanel;
    private TextureRegionPanel regionPanel;

    public SpriteEditorHUDPanel(SpriteFramePanel framePanel, SpriteAnimatePanel animatePanel, TextureRegionPanel regionPanel) {
        this.framePanel = framePanel;
        this.animatePanel = animatePanel;
        this.regionPanel = regionPanel;

        this.init();
    }

    private void init() {
        this.addPipeline(new PerfRender());

        this.skin = new Skin(Gdx.files.internal("skin/holo/uiskin.json"));

        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        int widthSum = 0;

        Button button = new TextButton("Create", this.skin, "colored");
        button.setPosition(width * 0.005f, height * 0.05f);
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                SpriteEditorHUDPanel.this.createClicked();
            }
        });
        this.stage.addActor(button);

        widthSum += button.getWidth();

        button = new TextButton("Load", this.skin, "colored");
        button.setPosition(width * 0.005f + widthSum, height * 0.05f);
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                SpriteEditorHUDPanel.this.loadClicked();
            }
        });
        this.stage.addActor(button);

        widthSum += button.getWidth();

        button = new TextButton("Save", this.skin, "colored");
        button.setPosition( width * 0.005f + widthSum, height * 0.05f);
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                SpriteEditorHUDPanel.this.saveClicked();
            }
        });
        this.stage.addActor(button);

        widthSum += button.getWidth();

        button = new TextButton("Clear", this.skin, "colored");
        button.setPosition(width * 0.005f + widthSum, height * 0.05f);
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                SpriteEditorHUDPanel.this.clearClicked();
            }
        });
        this.stage.addActor(button);
    }

    @Override
    public void dispose() {
        super.dispose();
        this.skin.dispose();
    }

    private void createClicked() {
    }

    private void loadClicked() {
    }

    private void saveClicked() {
    }

    private void clearClicked() {
    }
}
