package com.jongber.game.desktop.editor.sequence.detail;

import com.badlogic.gdx.math.Vector3;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.graphics.OrthoCameraWrapper;
import com.jongber.game.core.sequence.GameSequence;

public class CameraMoveSpringSeq implements GameSequence {

    private final Vector3 to;
    private final Float duration;
    private Vector3 pos;

    private float k;
    private OrthoCameraWrapper c;
    private float totDist;
    private float halfDist;

    private float spd = 0.0f;
    private float elapsed;
    private Vector3 dir;

    private GameLayer layer;

    public CameraMoveSpringSeq(Vector3 to, float duration) {
        this.to = to;
        this.duration = duration;
    }

    @Override
    public void create(GameLayer layer) {
        this.layer = layer;
    }

    @Override
    public void start() {
        if (this.duration <= 0.0f) {
            return;
        }

        this.k = calcK();
        this.c = layer.getCameraWrapper();
        this.pos = c.getPosition(new Vector3());
        this.pos.z = c.getZoom();

        this.dir = new Vector3(this.to).sub(this.pos);
        this.totDist = this.dir.len();
        this.halfDist = this.totDist / 2.0f;
        this.dir.scl(1/totDist);
    }

    @Override
    public void update(float elapsed) {
        if (this.duration <= 0.0f) {
            return;
        }

        this.elapsed += elapsed;

        pos = new Vector3(this.to);
        float len = pos.sub(cameraPos()).len();
        // F = kx = ma
        spd += k * elapsed * (len - this.halfDist);
        float move = spd * elapsed;

        c.getCamera().position.add(move * dir.x, move * dir.y , 0.0f);
        c.getCamera().zoom += move * dir.z;
    }

    @Override
    public void end() {
    }

    @Override
    public boolean isEnded() {

        if (this.elapsed >= this.duration) {

            c.getCamera().position.x = this.to.x;
            c.getCamera().position.y = this.to.y;
            c.setZoom(this.to.z);

            return true;
        }

        return false;
    }

    private Vector3 cameraPos() {
        return new Vector3(c.getPosition().x, c.getPosition().y, c.getZoom());
    }

    private float calcK() {
        return (float)((Math.PI * Math.PI) / (this.duration * this.duration));
    }
}
