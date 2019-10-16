package com.jongber.projectp.object.method;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.jongber.projectp.object.GameObject;
import com.jongber.projectp.object.data.SpriteComponent;

public class RenderMethod {

    public static void renderSprite(SpriteBatch batch, GameObject object, float elapsed) {
        try {

            SpriteComponent component = object.getComponent(SpriteComponent.class);
            TextureRegion region = component.getNext(elapsed);
            Vector2 pos = object.getTransform();

            batch.draw(region, pos.x - component.getPivotX(), pos.y - component.getPivotY());

        } catch (Exception e) {
            Gdx.app.log("DEBUG", "invalid render call[" + object.getName() + "]");
        }
    }

}
