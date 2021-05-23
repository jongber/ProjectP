package com.projecta.game.desktop.editor.panel;

import com.projecta.game.core.util.Tuple2;
import com.projecta.game.desktop.common.panel.GamePanel;
import com.projecta.game.desktop.editor.pipeline.TextureRender;

public class TextureRegionPanel extends GamePanel {

    public TextureRegionPanel(Tuple2<Float, Float> screenRatio, Tuple2<Float, Float> posRatio) {
        super(screenRatio, posRatio);

        this.init();
    }

    private void init() {
        this.addPipeline(new TextureRender(this.getCamera()));
        //this.addPipeline(new BlockGridRender(this.getCamera()));
    }
}
