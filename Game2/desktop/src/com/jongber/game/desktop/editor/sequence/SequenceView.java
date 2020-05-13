package com.jongber.game.desktop.editor.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.jongber.game.core.sequence.SequencePlan;
import com.jongber.game.desktop.common.controller.BlockGridRenderer;
import com.jongber.game.desktop.editor.EditorCmd;
import com.jongber.game.desktop.editor.EditorView;
import com.jongber.game.desktop.editor.sequence.detail.CameraMoveSpringSeq;
import com.jongber.game.desktop.editor.sequence.detail.FadeInSeq;
import com.jongber.game.desktop.editor.sequence.detail.FadeOutSeq;

public class SequenceView extends EditorView {

    public SequenceView() {
        this.registerController(new BlockGridRenderer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        SequencePlan plan = new SequencePlan();
//        CameraMoveSpringSeq seq1 = new CameraMoveSpringSeq(new Vector3(2000.0f, 0.0f, 0.3f), 3.0f);
//        seq1.create(this);
//        SequencePlan plan = new SequencePlan();
//        plan.addTimeSeq(1.0f, seq1);
//
//        CameraMoveSpringSeq seq2 = new CameraMoveSpringSeq(new Vector3(0.0f, 0.0f, 1.0f), 1.0f);
//        seq2.create(this);
//        plan.addLinkSeq(seq1, seq2);
//        //plan.addTimeSeq(4.0f, seq);

        FadeOutSeq f = new FadeOutSeq(10.0f);
        f.create(this);
        plan.addTimeSeq(1.5f, f);

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
