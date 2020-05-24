package com.jongber.game.projectrooms.sequence.camera;

import com.badlogic.gdx.math.Vector3;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.graphics.OrthoCameraWrapper;
import com.jongber.game.core.sequence.GameSequence;
import com.jongber.game.core.sequence.detail.MoveAccelTo;

public class CameraMoveAccSeq extends GameSequence {

    MoveAccelTo moveAccelTo;

    float totElapsed;
    float duration;
    Vector3 to;

    OrthoCameraWrapper c;

    public CameraMoveAccSeq(Vector3 to, float duration) {
        this.duration = duration;
        this.to = to;
    }

    @Override
    public void start(GameLayer layer) {
        this.c = layer.getCameraWrapper();
        this.moveAccelTo = new MoveAccelTo(this.cameraPos(), this.to, this.duration);
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
        this.totElapsed += elapsed;
        Vector3 pos = this.moveAccelTo.update(elapsed);
        this.c.getCamera().position.x = pos.x;
        this.c.getCamera().position.y = pos.y;
        this.c.getCamera().zoom = pos.z;
    }

    @Override
    public void dispose() {

    }

    private Vector3 cameraPos() {
        return new Vector3(c.getPosition().x, c.getPosition().y, c.getZoom());
    }
}
