package com.jongber.game.desktop.editor.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.jongber.game.core.sequence.SequencePlan;
import com.jongber.game.desktop.common.controller.BlockGridRenderer;
import com.jongber.game.desktop.editor.EditorCmd;
import com.jongber.game.desktop.editor.EditorView;
import com.jongber.game.desktop.editor.sequence.detail.CameraMoveSpringSeq;

public class SequenceView extends EditorView {

    public SequenceView() {
        this.registerController(new BlockGridRenderer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        CameraMoveSpringSeq seq = new CameraMoveSpringSeq(new Vector3(2000.0f, 0.0f, 0.5f), 1.0f * 50);
        seq.create(this);
        SequencePlan plan = new SequencePlan();
        plan.addTimeSeq(1.0f, seq);

        this.setSequencePlan(plan);
    }

    @Override
    public void create(EditorCmd cmd) {
    }

    @Override
    public void update(float elapsed) {
        super.update(elapsed);
    }
}
