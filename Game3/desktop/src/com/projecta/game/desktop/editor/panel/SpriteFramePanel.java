package com.projecta.game.desktop.editor.panel;

import com.projecta.game.core.util.Tuple2;
import com.projecta.game.desktop.common.GamePanel;
import com.projecta.game.desktop.common.pipeline.BlockGridRender;

public class SpriteFramePanel extends GamePanel {
    public SpriteFramePanel(Tuple2<Float, Float> screenRatio, Tuple2<Float, Float> posRatio) {
        super(screenRatio, posRatio);

        this.init();
    }

    private void init() {
        //this.addPipeline(new BlockGridRender(this.getCamera()));
    }
}
