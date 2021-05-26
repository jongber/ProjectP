package com.projecta.game.desktop.editor.spriteeditor.panel;

import com.projecta.game.core.util.Tuple2;
import com.projecta.game.desktop.common.panel.GamePanel;
import com.projecta.game.desktop.common.pipeline.BlockGridRender;

public class SpriteAnimatePanel extends GamePanel {

    public SpriteAnimatePanel(Tuple2<Float, Float> screenRatio, Tuple2<Float, Float> posRatio) {
        super(screenRatio, posRatio);

        this.init();
    }

    private void init() {
        this.addPipeline(new BlockGridRender(this.getCamera()));
    }
}
