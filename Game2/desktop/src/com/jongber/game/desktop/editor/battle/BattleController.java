package com.jongber.game.desktop.editor.battle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.controller.Controller;
import com.jongber.game.core.controller.adapter.InputControlAdapter;
import com.jongber.game.core.graphics.OrthoCameraWrapper;
import com.jongber.game.core.sequence.SequencePlan;

public class BattleController extends InputControlAdapter
        implements Controller.Updater, Controller.InputProcessor, Controller.PostRenderer, Controller.Renderer {

    private GameLayer layer;
    private BattleRule rule;

    public BattleController(GameLayer layer, BattleRule rule) {
        this.layer = layer;
        this.rule = rule;
    }

    @Override
    public void update(float elapsed) {

    }

    @Override
    public void render(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed) {

    }

    @Override
    public void postRender(SpriteBatch batch, OrthoCameraWrapper camera, float elapsed) {

    }

    @Override
    public boolean touchDown(OrthoCameraWrapper camera, float worldX, float worldY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(OrthoCameraWrapper camera, float worldX, float worldY, int pointer, int button) {

        if (this.layer.isSequenceEnded() == false) {
            return false;
        }

        SequencePlan plan = this.rule.createAttackPlan();
        this.layer.setSequencePlan(plan);

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
