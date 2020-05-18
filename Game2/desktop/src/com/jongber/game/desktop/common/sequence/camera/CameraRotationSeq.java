package com.jongber.game.desktop.common.sequence.camera;

import com.badlogic.gdx.math.Vector3;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.graphics.OrthoCameraWrapper;
import com.jongber.game.core.sequence.GameSequence;

public class CameraRotationSeq extends GameSequence {

    final float toAngle;
    final float duration;

    private OrthoCameraWrapper c;
    private float totElapsed;
    private float spd;

    private Vector3 orgUp;

    public CameraRotationSeq(float angle, float duration) {
        this.toAngle = angle;
        this.duration = duration;
    }

    @Override
    public void start(GameLayer layer) {
        c = layer.getCameraWrapper();
        orgUp = new Vector3(c.getCamera().up);

        spd = this.toAngle/duration;
    }

    @Override
    public void end() {

    }

    @Override
    public boolean isEnded() {
        if (this.totElapsed >= this.duration) {

            orgUp.rotate(new Vector3(0, 0, -1.0f), this.spd * this.duration);
            c.getCamera().up.x = orgUp.x;
            c.getCamera().up.y = orgUp.y;
            c.getCamera().up.z = orgUp.z;
            c.getCamera().update();

            return true;
        }

        return false;
    }

    @Override
    public void update(float elapsed) {

        c.getCamera().rotate(spd * elapsed);

        this.totElapsed += elapsed;
    }

    @Override
    public void dispose() {

    }
}
