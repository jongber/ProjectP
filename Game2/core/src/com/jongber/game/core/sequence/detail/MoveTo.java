package com.jongber.game.core.sequence.detail;

import com.badlogic.gdx.math.Vector3;

public class MoveTo {

    private final Vector3 curPos;
    private final Vector3 to;
    private final float duration;

    private Vector3 dir;
    private float spd;
    private float dist;
    private float totElapsed;

    public MoveTo(Vector3 from, Vector3 to, float duration) {
        this.curPos = new Vector3(from);
        this.to = new Vector3(to);
        this.duration = duration;

        if (duration > 0.0f) {
            this.dir = new Vector3(to).sub(from);
            this.dist = this.dir.len();
            this.spd = this.dist / duration;
            if (this.dist > 0.0f) {
                this.dir.scl(1/dist);
            }
        }
    }

    public Vector3 update(float elapsed) {
        if (this.totElapsed >= duration || this.dist <= 0.0f) {
            return this.curPos;
        }

        this.curPos.add(this.dir.x * spd * elapsed, this.dir.y * spd * elapsed, this.dir.z * spd * elapsed);

        this.totElapsed += elapsed;

        return curPos;
    }
}
