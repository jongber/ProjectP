package com.jongber.game.desktop.common.sequence.camera;

import com.badlogic.gdx.math.Vector3;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.graphics.OrthoCameraWrapper;
import com.jongber.game.core.sequence.GameSequence;
import com.jongber.game.core.sequence.detail.MoveTo;

public class CameraMoveSeq extends GameSequence {

    private final float duration;
    private final Vector3 to;

    private MoveTo moveTo;
    private OrthoCameraWrapper c;
    private float totElapsed;


    public CameraMoveSeq(Vector3 to, float duration) {
        this.duration = duration;
        this.to = to;
    }

    @Override
    public void start(GameLayer layer) {
        this.c = layer.getCameraWrapper();
        moveTo = new MoveTo(this.cameraPos(), to, duration);
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
        totElapsed += elapsed;
        Vector3 moved = this.moveTo.update(elapsed);
        this.c.getCamera().position.x = moved.x;
        this.c.getCamera().position.y = moved.y;
        this.c.getCamera().zoom = moved.z;
    }

    @Override
    public void dispose() {

    }

    private Vector3 cameraPos() {
        return new Vector3(c.getPosition().x, c.getPosition().y, c.getZoom());
    }
}
