package com.jongber.game.core.sequence.detail;

import com.badlogic.gdx.math.Vector3;

public class StringMovementWithin {

    private Vector3 from;
    private Vector3 to;
    private float time;

    private float k;
    private float dist;
    private float halfDist;
    private float spd = 0.0f;
    private Vector3 dir;
    private Vector3 movedPos;

    private float totElapsed;

    public StringMovementWithin(Vector3 from, Vector3 to, float time) {
        this.from = from;
        this.to = to;
        this.time = time;
        this.movedPos = new Vector3(this.from);

        if (time > 0.0f)
            this.k = calcK();

        this.dir = new Vector3(this.to).sub(this.from);
        this.dist = this.dir.len();
        this.halfDist = this.dist / 2.0f;

        if (dist > 0.0f)
            this.dir.scl(1/dist);
    }

    public Vector3 update(float elapsed) {
        if (this.time <= 0.0f || this.dist <= 0.0f) {
            return this.to;
        }

        if (this.totElapsed >= this.time) {
            return this.to;
        }

        this.totElapsed += elapsed;

        float len = new Vector3(this.to).sub(movedPos).len();
        // F = kx = ma
        spd += k * elapsed * (len - this.halfDist);
        float move = spd * elapsed;

        this.movedPos.set(move * dir.x, move * dir.y, move * dir.z);

        return this.movedPos;
    }

    private float calcK() {
        return (float)((Math.PI * Math.PI) / (this.time * this.time));
    }
}
