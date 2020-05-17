package com.jongber.game.desktop.editor.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.jongber.game.core.sequence.SequencePlan;
import com.jongber.game.desktop.common.controller.BlockGridRenderer;
import com.jongber.game.desktop.common.sequence.CameraMoveAccSeq;
import com.jongber.game.desktop.editor.EditorCmd;
import com.jongber.game.desktop.editor.EditorView;

public class SequenceView extends EditorView {

    public SequenceView() {
        this.registerController(new BlockGridRenderer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        SequencePlan plan = new SequencePlan();
        CameraMoveAccSeq seq1 = new CameraMoveAccSeq(this, new Vector3(100, 0.0f, 0.1f), 1.0f);
        plan.addTimeSeq(0.5f, seq1);

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
