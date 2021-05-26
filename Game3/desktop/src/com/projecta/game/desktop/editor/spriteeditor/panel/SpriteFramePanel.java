package com.projecta.game.desktop.editor.spriteeditor.panel;

import com.projecta.game.core.util.Tuple2;
import com.projecta.game.desktop.common.panel.HUDPanel;

public class SpriteFramePanel extends HUDPanel {

    public SpriteFramePanel(Tuple2<Float, Float> screenRatio, Tuple2<Float, Float> posRatio) {
        super(screenRatio, posRatio);

        this.init();
    }

    private void init() {
        //this.addPipeline(new BlockGridRender(this.getCamera()));
    }
}
