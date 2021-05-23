package com.projecta.game.desktop.editor.panel;

import com.projecta.game.desktop.common.panel.HUDPanel;
import com.projecta.game.desktop.common.pipeline.PerfRender;

public class SpriteEditorHUDPanel extends HUDPanel {

    public SpriteEditorHUDPanel() {
        this.init();
    }

    private void init() {
        this.addPipeline(new PerfRender());
    }
}
