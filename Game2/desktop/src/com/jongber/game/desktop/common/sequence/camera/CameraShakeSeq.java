package com.jongber.game.desktop.common.sequence.camera;

import com.badlogic.gdx.math.Vector3;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.graphics.OrthoCameraWrapper;
import com.jongber.game.core.sequence.GameSequence;

public class CameraShakeSeq extends GameSequence {

    float totElapsed;
    OrthoCameraWrapper camera;
    float duration;
    float intensity;

    Vector3 orgPos;

    public CameraShakeSeq(float intensity, float duration) {
        this.intensity = intensity;
        this.duration = duration;
    }

    @Override
    public void start(GameLayer layer) {
        this.camera = layer.getCameraWrapper();
        orgPos = new Vector3(this.camera.getPosition());
    }

    @Override
    public void end() {
        this.camera.setPosition(this.orgPos.x, this.orgPos.y);
    }

    @Override
    public boolean isEnded() {
        if (this.totElapsed >= this.duration) {
            return true;
        }

        return false;
    }

    @Override
    public void update(float elapsed) {
        totElapsed += elapsed;

        float x = (float)Math.random() * intensity;
        float y = (float)Math.random() * intensity;

        this.camera.setPosition(this.orgPos.x + x, this.orgPos.y + y);
    }

    @Override
    public void dispose() {

    }
}
