package com.jongber.projectp.game.method;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.jongber.projectp.game.data.AnimationComponent;

public class RenderMethod {

    public static void render(SpriteBatch batch,
                              Vector2 transform,
                              AnimationComponent anim,
                              float elapsed) {

        TextureRegion region = anim.getNext(elapsed);
        if (region == null)
            return;

        batch.draw(region, transform.x - anim.getPivotX(), transform.y - anim.getPivotY());
    }

}
