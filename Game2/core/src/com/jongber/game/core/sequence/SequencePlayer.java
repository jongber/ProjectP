package com.jongber.game.core.sequence;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jongber.game.core.controller.Controller;
import com.jongber.game.core.graphics.OrthoCameraWrapper;
import com.jongber.game.core.util.PackedArray;

import java.util.ArrayList;
import java.util.List;

public class SequencePlayer extends Controller
        implements Controller.Updater, Controller.InputProcessor, Controller.Renderer, Controller.PostRenderer {

    private float totElapsed = 0.0f;
    private PackedArray<GameSequence> playing = new PackedArray<>();
    private PackedArray<Controller.Renderer> renders = new PackedArray<>();
    private PackedArray<Controller.PostRenderer> postRenders = new PackedArray<>();
    private PackedArray<Controller.InputProcessor> inputProcessors = new PackedArray<>();

    private List<GameSequence> endSeqs = new ArrayList<>();
    private SequencePlan seqPlan = new SequencePlan();

    public void setPlan(SequencePlan plan) {
        this.seqPlan = plan;
        this.totElapsed = 0;
        this.playing.clearAll();
    }

    public boolean isEnded() {
        return seqPlan == null || (this.playing.size() == 0 && seqPlan.ended());
    }

    @Override
    public void update(float elapsed) {
        if (isEnded()) {
            return;
        }

        this.totElapsed += elapsed;

        this.selectSequence();
        this.updateSequence(elapsed);
        this.processEnded();
    }

    private void selectSequence() {
        GameSequence seq = this.seqPlan.getNext(this.totElapsed);
        while (seq != null) {
            this.startSequence(seq);
            seq = this.seqPlan.getNext(this.totElapsed);
        }
    }

    private void updateSequence(float elapsed) {
        for (GameSequence seq : this.playing) {
            seq.update(elapsed);

            if (seq.isEnded()) {
                this.endSeqs.add(seq);
            }
        }
    }

    private void processEnded() {
        for (GameSequence seq : this.endSeqs) {

            this.endSequence(seq);
        }

        this.endSeqs.clear();
    }

    private void startSequence(GameSequence seq) {

        if (seq instanceof Controller.Renderer) {
            this.renders.add((Controller.Renderer)seq);
        }

        if (seq instanceof Controller.PostRenderer) {
            this.postRenders.add((Controller.PostRenderer)seq);
        }

        if (seq instanceof Controller.InputProcessor) {
            this.inputProcessors.add((Controller.InputProcessor)seq);
        }

        seq.start();
        this.playing.add(seq);
    }

    private void endSequence(GameSequence seq) {
        seq.end();
        seq.dispose();

        this.playing.remove(seq);
        this.seqPlan.processEnd(seq);

        if (seq instanceof Controller.Renderer) {
            this.renders.remove((Controller.Renderer)seq);
        }

        if (seq instanceof Controller.PostRenderer) {
            this.postRenders.remove((Controller.PostRenderer)seq);
        }

        if (seq instanceof Controller.InputProcessor) {
            this.inputProcessors.remove((Controller.InputProcessor)seq);
        }
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

    @Override
    public void render(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed) {
        for (Renderer r : this.renders) {
            r.render(batch, camera, elapsed);
        }
    }

    @Override
    public void postRender(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed) {
        for (PostRenderer r : this.postRenders) {
            r.postRender(batch, camera, elapsed);
        }
    }
}
