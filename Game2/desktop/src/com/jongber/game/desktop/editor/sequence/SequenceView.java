package com.jongber.game.desktop.editor.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.jongber.game.core.sequence.SequencePlan;
import com.jongber.game.desktop.common.controller.BlockGridRenderer;
import com.jongber.game.desktop.common.sequence.CameraShakeSeq;
import com.jongber.game.desktop.editor.EditorCmd;
import com.jongber.game.desktop.editor.EditorView;
import com.jongber.game.desktop.common.sequence.CameraMoveSpringSeq;
import com.jongber.game.desktop.common.sequence.FadeInSeq;
import com.jongber.game.desktop.common.sequence.FadeOutSeq;

public class SequenceView extends EditorView {

    public SequenceView() {
        this.registerController(new BlockGridRenderer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        SequencePlan plan = new SequencePlan();
        CameraMoveSpringSeq seq1 = new CameraMoveSpringSeq(new Vector3(1000.0f, 0.0f, 0.3f), 3.0f);
        seq1.create(this);

        plan.addTimeSeq(1.0f, seq1);

        CameraShakeSeq seq = new CameraShakeSeq(10.0f, 0.7f);
        seq.create(this);

        plan.addLinkSeq(seq1, seq);

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
