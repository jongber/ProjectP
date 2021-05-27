package com.projecta.game.desktop.editor.spriteeditor.panel;

import com.projecta.game.desktop.common.panel.HUDPanel;
import com.projecta.game.desktop.common.pipeline.PerfRender;


public class HUDSpriteEditPanel extends HUDPanel {
    //private Skin skin;

    private SpriteFramePanel framePanel;
    private SpriteAnimatePanel animatePanel;
    private TextureRegionPanel regionPanel;

    public HUDSpriteEditPanel(SpriteFramePanel framePanel, SpriteAnimatePanel animatePanel, TextureRegionPanel regionPanel) {
        this.framePanel = framePanel;
        this.animatePanel = animatePanel;
        this.regionPanel = regionPanel;

        this.init();
    }

    private void init() {
        this.addPipeline(new PerfRender());
    }

//    private void init() {
//        this.addPipeline(new PerfRender());
//
//        this.skin = new Skin(Gdx.files.internal("skin/holo/uiskin.json"));
//
//        int width = Gdx.graphics.getWidth();
//        int height = Gdx.graphics.getHeight();
//
//        int widthSum = 0;
//
//        Button button = new TextButton("Create", this.skin, "colored");
//        button.setPosition(width * 0.005f, height - height * 0.1f);
//        button.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                super.clicked(event, x, y);
//                HUDSpriteEditPanel.this.createClicked();
//            }
//        });
//        this.stage.addActor(button);
//
//        widthSum += button.getWidth();
//
//        button = new TextButton("Load", this.skin, "colored");
//        button.setPosition(width * 0.005f + widthSum, height - height * 0.1f);
//        button.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                super.clicked(event, x, y);
//                HUDSpriteEditPanel.this.loadClicked();
//            }
//        });
//        this.stage.addActor(button);
//
//        widthSum += button.getWidth();
//
//        button = new TextButton("Save", this.skin, "colored");
//        button.setPosition( width * 0.005f + widthSum, height - height * 0.1f);
//        button.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                super.clicked(event, x, y);
//                HUDSpriteEditPanel.this.saveClicked();
//            }
//        });
//        this.stage.addActor(button);
//
//        widthSum += button.getWidth();
//
//        button = new TextButton("Clear", this.skin, "colored");
//        button.setPosition(width * 0.005f + widthSum, height - height * 0.1f);
//        button.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                super.clicked(event, x, y);
//                HUDSpriteEditPanel.this.clearClicked();
//            }
//        });
//        this.stage.addActor(button);
//    }

    @Override
    public void dispose() {
        super.dispose();
        //this.skin.dispose();
    }
}
