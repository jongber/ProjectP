package com.jongber.projectp.game.data;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.jongber.projectp.graphics.VFAnimation;

public class AnimationComponent {
    private VFAnimation animation;
    private Vector2 pivot;

    public AnimationComponent(VFAnimation animation, Vector2 pivot) {
        this.animation = animation;
        this.pivot = pivot;
    }

    public int getPivotX() {
        return (int)this.pivot.x;
    }

    public int getPivotY() {
        return (int)this.pivot.y;
    }

    public TextureRegion getNext(float elapsed) {
        return this.animation.getNext(elapsed);
    }

    public boolean isFinished() {
        return this.animation.getPlaybackCount() > 0
                && this.animation.getMode() == VFAnimation.PlayMode.ONCE;
    }
}
