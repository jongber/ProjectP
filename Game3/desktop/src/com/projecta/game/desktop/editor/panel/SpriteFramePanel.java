package com.projecta.game.desktop.editor.panel;

import com.badlogic.gdx.graphics.Texture;
import com.projecta.game.core.util.Tuple2;
import com.projecta.game.desktop.common.panel.GamePanel;
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
