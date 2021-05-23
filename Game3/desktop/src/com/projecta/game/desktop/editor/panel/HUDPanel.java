package com.projecta.game.desktop.editor.panel;

import com.projecta.game.core.util.Tuple2;
import com.projecta.game.desktop.common.GamePanel;
import com.projecta.game.desktop.common.pipeline.PerfRender;

public class HUDPanel extends GamePanel {

    public HUDPanel() {
        super(new Tuple2<>(1.0f, 1.0f), new Tuple2<>(0.0f, 0.0f));

        this.init();
    }

    private void init() {
        this.addPipeline(new PerfRender());
    }
}
