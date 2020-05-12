package com.jongber.game.desktop.editor.sequence.detail;

import com.badlogic.gdx.math.Vector3;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.graphics.OrthoCameraWrapper;
import com.jongber.game.core.sequence.GameSequence;

public class CameraMoveSeq extends GameSequence {

    private final Vector3 to;
    private final float duration;

    private float elapsed;
    private OrthoCameraWrapper c;

    private Vector3 direction;
    private float spd;

    public CameraMoveSeq(Vector3 to, float duration) {
        this.to = to;
        this.duration = duration;
    }

    @Override
    public void create(GameLayer layer) {
        this.c = layer.getCameraWrapper();
    }

    @Override
    public void start() {
        if (this.duration <= 0.0f)
            return;

        Vector3 from = new Vector3(this.c.getPosition());
        from.z = this.c.getZoom();

        Vector3 to = new Vector3(this.to);

        this.direction = to.sub(from);

        float dist = direction.len();
        this.spd = dist/this.duration;
        this.direction.scl(1/dist);
    }

    @Override
    public void update(float elapsed) {
        if (this.duration <= 0.0f) {
            return;
        }

        this.elapsed += elapsed;

        Vector3 velocity = new Vector3(this.direction).scl(spd * elapsed);
        this.c.getPosition().add(velocity.x, velocity.y, 0.0f);
        this.c.addZoom(velocity.z);
    }

    @Override
    public void end() {
    }

    @Override
    public boolean isEnded() {
        if (elapsed >= this.duration) {

            c.getCamera().position.x = this.to.x;
            c.getCamera().position.y = this.to.y;
            c.setZoom(this.to.z);

            return true;
        }

        return false;
    }

    @Override
    public void dispose() {

    }
}
