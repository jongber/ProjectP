package com.projecta.game.desktop.editor.panel;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.projecta.game.core.util.Tuple2;
import com.projecta.game.desktop.common.component.TextureComponent;
import com.projecta.game.desktop.common.panel.GamePanel;
import com.projecta.game.desktop.common.panel.HUDPanel;
import com.projecta.game.desktop.editor.object.TextureObject;
import com.projecta.game.desktop.editor.pipeline.TextureRender;

public class TextureRegionPanel extends GamePanel {

    public TextureRegionPanel(Tuple2<Float, Float> screenRatio, Tuple2<Float, Float> posRatio) {
        super(screenRatio, posRatio);

        this.init();
    }

    public void onLoadTexture(Texture texture) {

        this.removeAllObjects();

        TextureObject obj = new TextureObject();
        obj.addComponent(TextureComponent.class, new TextureComponent(new TextureRegion(texture)));
        obj.bindComponent();

        this.addObject(obj);
    }

    private void init() {
        this.addPipeline(new TextureRender(this.getCamera()));
        //this.addPipeline(new BlockGridRender(this.getCamera()));
    }
}
