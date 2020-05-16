package com.jongber.game.desktop.editor.battle.seq;

import com.badlogic.gdx.math.Vector2;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.sequence.GameSequence;
import com.jongber.game.desktop.common.component.SpriteComponent;

public class GameObjectMoveSeq extends GameSequence {

    private GameObject object;
    private Vector2 to;
    private Vector2 from;

    private float time;
    private float scale;

    private float totElapsed;

    public GameObjectMoveSeq(GameLayer layer, GameObject object, Vector2 to, float scale, float time) {
        super(layer);
        this.object = object;
        this.time = time;
        this.to = to;
        this.from = object.transform.getLocalPos();

        this.time = time;
        this.scale = scale;
    }

    @Override
    public void start() {
        object.transform.local.setToTranslation(this.to);
    }

    @Override
    public void end() {

    }

    @Override
    public boolean isEnded() {
        if (this.totElapsed >= this.time) {
            return true;
        }

        return false;
    }

    @Override
    public void update(float elapsed) {
        this.totElapsed += elapsed;

//        this.from.lerp(this.to, elapsed/this.time);
//
//        object.transform.local.setToTranslation(this.from);
//        object.getComponent(SpriteComponent.class).scale.scl(1.0f + this.scale * elapsed/this.time);
    }

    @Override
    public void dispose() {

    }
}
