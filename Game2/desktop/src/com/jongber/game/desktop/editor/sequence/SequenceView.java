package com.jongber.game.desktop.editor.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.jongber.game.core.controller.Controller;
import com.jongber.game.core.sequence.SequencePlan;
import com.jongber.game.core.util.Tuple2;
import com.jongber.game.desktop.common.controller.BlockGridRenderer;
import com.jongber.game.desktop.editor.EditorCmd;
import com.jongber.game.desktop.editor.EditorView;
import com.jongber.game.desktop.editor.sequence.detail.CameraMoveSeq;

public class SequenceView extends EditorView {

    public SequenceView() {
        this.registerController(new BlockGridRenderer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        CameraMoveSeq seq = new CameraMoveSeq(new Tuple2<>(new Vector2(2000.0f, 0.0f), 1.0f), 1.0f * 500);
        seq.create(this);
        SequencePlan plan = new SequencePlan();
        plan.plans.add(new Tuple2<>(0.0f, seq));

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
