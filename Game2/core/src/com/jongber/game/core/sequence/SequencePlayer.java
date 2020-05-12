package com.jongber.game.core.sequence;

import com.jongber.game.core.controller.Controller;
import com.jongber.game.core.graphics.OrthoCameraWrapper;
import com.jongber.game.core.util.PackedArray;

import java.util.ArrayList;
import java.util.List;

public class SequencePlayer extends Controller implements Controller.Updater, Controller.InputProcessor {

    private float elapsed = 0.0f;
    private PackedArray<GameSequence> curSeq = new PackedArray<>();
    private List<GameSequence> endSeqs = new ArrayList<>();
    private SequencePlan seqPlan = new SequencePlan();

    public void setPlan(SequencePlan plan) {
        this.seqPlan = plan;
        this.elapsed = 0;
        this.curSeq.clearAll();
    }

    public boolean isEnded() {
        return seqPlan == null || (this.curSeq.size() == 0 && seqPlan.ended());
    }

    @Override
    public void update(float elapsed) {
        if (isEnded()) {
            return;
        }

        this.elapsed += elapsed;

        this.selectSequence();
        this.updateSequence();
        this.processEnded();
    }

    private void selectSequence() {
        GameSequence seq = this.seqPlan.getNext(this.elapsed);
        while (seq != null) {
            this.curSeq.add(seq);
            seq = this.seqPlan.getNext(this.elapsed);
        }
    }

    private void updateSequence() {
        for (GameSequence seq : this.curSeq) {
            seq.update(elapsed);

            if (seq.isEnded()) {
                this.endSeqs.add(seq);
            }
        }
    }

    private void processEnded() {
        for (GameSequence seq : this.endSeqs) {
            this.curSeq.remove(seq);

            List<GameSequence> next = this.seqPlan.getNext(seq);
            if (next != null) {
                for (GameSequence nextSeq : next) {
                    this.curSeq.add(nextSeq);
                }
            }
        }

        this.endSeqs.clear();
    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean touchDown(OrthoCameraWrapper camera, float worldX, float worldY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(OrthoCameraWrapper camera, float worldX, float worldY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(OrthoCameraWrapper camera, float worldX, float worldY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(OrthoCameraWrapper camera, float worldX, float worldY) {
        return false;
    }

    @Override
    public boolean scrolled(OrthoCameraWrapper camera, int amount) {
        return false;
    }
}
