package com.projecta.game.desktop.common.component;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.projecta.game.core.base.object.GameObjectComponent;
import com.projecta.game.core.util.Tuple2;

import java.util.ArrayList;

public class SpriteComponent extends GameObjectComponent {
    public Vector2 pivot;
    public ArrayList<Tuple2<TextureRegion, Integer>> frames = new ArrayList<>();
    public int currentFrame = 0;
    public int elapsedTime = 0;

    public TextureRegion getNext(float elapsed, boolean loop) {
        int frameLength = this.frames.size();
        if (frameLength <= 0) {
            return null;
        }

        this.elapsedTime += elapsed * 1000.0f;

        Tuple2<TextureRegion, Integer> frame = this.frames.get(this.currentFrame);
        int curDuration = frame.getItem2();
        while (this.elapsedTime > curDuration) {
            this.elapsedTime -= curDuration;
            this.currentFrame++;

            if (this.currentFrame >= frameLength) {
                if (loop) {
                    this.currentFrame = 0;
                }
                else {
                    this.currentFrame--;
                }
            }

            frame = this.frames.get(this.currentFrame);
            curDuration = frame.getItem2();
        }

        return frame.getItem1();
    }

    public TextureRegion getCurrent() {
        return this.frames.get(this.currentFrame).getItem1();
    }

    public void setPivot(Vector2 pivot) {
        this.pivot = new Vector2(pivot);
    }

    public void addFrame(TextureRegion r, int frameInterval) {
        this.frames.add(new Tuple2<>(r, frameInterval));
    }
}
