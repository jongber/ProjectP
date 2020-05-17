package com.jongber.game.desktop.editor.battle.seq;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.sequence.GameSequence;
import com.jongber.game.core.sequence.detail.MoveTo;
import com.jongber.game.desktop.common.component.SpriteComponent;

public class GameObjectMoveSeq extends GameSequence {

    private Vector3 to;
    private float duration;
    private GameObject character;
    private MoveTo moveTo;

    private float totElapsed;

    public GameObjectMoveSeq(GameLayer layer, GameObject character, Vector2 to, float duration) {
        super(layer);
        this.to = new Vector3(to, 0.0f);
        this.duration = duration;
        this.character = character;

        Vector3 from= new Vector3(character.transform.getLocalPos(), 0.0f);
        moveTo = new MoveTo(from, this.to, duration);
    }

    @Override
    public void start() {

    }

    @Override
    public void end() {

    }

    @Override
    public boolean isEnded() {
        if (this.totElapsed >= this.duration)
            return true;

        return false;
    }

    @Override
    public void update(float elapsed) {

        Vector3 moved = moveTo.update(elapsed);
        this.character.transform.local.setToTranslation(moved.x, moved.y);

        this.totElapsed += elapsed;
    }

    @Override
    public void dispose() {

    }
}
