package com.jongber.game.desktop.editor.sequence.detail;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.graphics.OrthoCameraWrapper;
import com.jongber.game.core.sequence.GameSequence;
import com.jongber.game.core.util.Tuple2;

public class CameraMoveSeq implements GameSequence {

    private final Tuple2<Vector2, Float> to;
    private final float duration;

    private float elapsed;
    private OrthoCameraWrapper cameraWrapper;

    private float dist = 0.0f;
    private float c = 0.0f;
    private Vector3 direction;

    private Vector3 f;
    private Vector3 t;

    public CameraMoveSeq(Tuple2<Vector2, Float> to, float duration) {
        this.to = to;
        this.duration = duration;
    }

    @Override
    public void create(GameLayer layer) {
        this.cameraWrapper = layer.getCameraWrapper();

        Vector3 from = new Vector3(this.cameraWrapper.getPosition());
        from.z = this.cameraWrapper.getZoom();
        Vector3 to = new Vector3(this.to.getItem1(), this.to.getItem2());

        this.direction = to.sub(from);
        this.dist = direction.len();
        this.direction.scl(1/this.dist);

        this.c = (6 * this.dist) / (this.duration * this.duration * this.duration);
    }

    @Override
    public void update(float elapsed) {
        this.elapsed += elapsed;

        float spd = -c * this.elapsed * this.elapsed + c * duration * this.elapsed;

        Vector3 velocity = new Vector3(this.direction).scl(spd * elapsed);
        this.cameraWrapper.getPosition().add(velocity.x, velocity.y, 0.0f);
        this.cameraWrapper.addZoom(velocity.z);

        System.out.println("v : " + velocity);
    }

    @Override
    public boolean ended() {

        System.out.println(elapsed + " : " + this.duration + " " + this.cameraWrapper.getPosition());

        if (elapsed >= this.duration) {
            return true;
        }

        return false;
    }
}
