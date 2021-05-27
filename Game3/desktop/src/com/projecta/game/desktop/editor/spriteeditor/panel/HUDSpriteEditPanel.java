package com.projecta.game.desktop.editor.spriteeditor.panel;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.projecta.game.desktop.common.Env;
import com.projecta.game.desktop.common.component.SpriteComponent;
import com.projecta.game.desktop.common.component.TextureComponent;
import com.projecta.game.desktop.common.panel.HUDPanel;
import com.projecta.game.desktop.common.pipeline.PerfRender;
import com.projecta.game.desktop.editor.object.SpriteObject;
import com.projecta.game.desktop.editor.object.TextureObject;

import java.util.ArrayList;
import java.util.List;


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

    public void createSprite(String imgPath, Vector2 pivot, int pixelUnitX, int pixelUnitY, int from, int to, int frameInterval) {

        this.regionPanel.removeAllObjects();
        this.animatePanel.removeAllObjects();

        Env.assetManager.load(imgPath, Texture.class);
        Env.assetManager.finishLoading();
        Texture t = Env.assetManager.get(imgPath);

        List<TextureRegion> regions = new ArrayList<>();

        int xUnit = t.getWidth() / pixelUnitX;
        for (int i = from; i <= to; ++i) {
            int x = (i % xUnit) * pixelUnitX;
            int y = t.getHeight() - (i / xUnit + 1) * pixelUnitY;

            TextureRegion r = new TextureRegion(t, x, y, pixelUnitX, pixelUnitY);
            regions.add(r);
        }

        // sprite put
        SpriteComponent c = new SpriteComponent();
        c.setPivot(pivot);
        for (TextureRegion r : regions) {
            c.addFrame(r, frameInterval);
        }
        this.putSprite(c);

        // texture put
        TextureComponent tc = new TextureComponent(new TextureRegion(t));
        this.putTexture(tc);
    }

    private void putSprite(SpriteComponent c) {
        SpriteObject so = new SpriteObject();
        so.addComponent(SpriteComponent.class, c);

        this.animatePanel.addObject(so);
    }

    private void putTexture(TextureComponent c) {
        TextureObject o = new TextureObject();
        o.addComponent(TextureComponent.class, c);

        this.regionPanel.addObject(o);
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

    private void init() {
        this.addPipeline(new PerfRender());
    }
}
