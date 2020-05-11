package com.jongber.game.desktop.editor.sequence;

import com.jongber.game.core.util.PackedArray;

import java.util.ArrayList;
import java.util.List;

public class SequencePlayer {

    private float elapsed = 0.0f;
    PackedArray<GameSequence> curSeq = new PackedArray<>();
    List<GameSequence> endSeqs = new ArrayList<>();
    SequencePlan seqPlan;

    public void setPlan(SequencePlan plan) {
        this.seqPlan = plan;
        this.elapsed = 0;
        this.curSeq.clearAll();
    }

    public boolean ended() {
        return seqPlan == null || (this.curSeq.size() == 0 && seqPlan.ended());
    }

    public void update(float elapsed) {
        this.elapsed += elapsed;

        this.selectSequence();
        this.updateSequence();
        this.deleteEnded();
    }

    private void selectSequence() {
        if (ended()) {
            return;
        }

        GameSequence seq = this.seqPlan.getNext(this.elapsed);
        while (seq != null) {
            this.curSeq.add(seq);
            seq = this.seqPlan.getNext(this.elapsed);
        }
    }

    private void updateSequence() {
        for (GameSequence seq : this.curSeq) {
            seq.update(elapsed);

            if (seq.ended()) {
                this.endSeqs.add(seq);
            }
        }
    }

    private void deleteEnded() {
        for (GameSequence seq : this.endSeqs) {
            this.curSeq.remove(seq);
        }

        this.endSeqs.clear();
    }
}
