package com.jongber.game.core.sequence.detail;

import com.badlogic.gdx.math.Vector3;

public class MoveAccelTo {
    Vector3 to;
    float duration;

    float totElapsed;
    float acc;
    float spd = 0.0f;
    float dist;

    Vector3 dir;
    Vector3 curPos;

    public MoveAccelTo(Vector3 from, Vector3 to, float duration) {
        this.to = new Vector3(to);
        this.duration = duration;

        this.curPos = new Vector3(from);
        this.dir = new Vector3(to).sub(from);
        dist = dir.len();

        if (dist > 0) {
            this.dir.scl(1/dist);
            acc = 4 * dist / (duration * duration);
        }
    }

    public Vector3 update(float elapsed) {

        if (dist <= 0 || this.totElapsed >= this.duration)
            return to;

        if (this.totElapsed <= this.duration / 2.0f) {
            spd += acc * elapsed;
            curPos.add(dir.x * spd * elapsed, dir.y * spd * elapsed, dir.z * spd * elapsed);
        }
        else {
            spd -= acc * elapsed;
            curPos.add(dir.x * spd * elapsed, dir.y * spd * elapsed, dir.z * spd * elapsed);
        }

        this.totElapsed += elapsed;

        return curPos;
    }
}
