package com.jongber.projectp.oldaction.object.method;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.jongber.projectp.oldaction.asset.StaticTextureAsset;
import com.jongber.projectp.oldaction.graphics.OrthoCameraWrapper;
import com.jongber.projectp.oldaction.object.GameObject;
import com.jongber.projectp.oldaction.object.component.SceneryComponent;
import com.jongber.projectp.oldaction.object.component.SpriteComponent;

public class RenderMethod {

    public static void renderSprite(int layer, SpriteBatch batch, GameObject object, float elapsed) {
        try {

            SpriteComponent component = object.getComponent(SpriteComponent.class);

            TextureRegion region = component.getNext(layer, elapsed);

            if (region == null)
                return;

            Vector2 pos = object.getTransform();
            batch.draw(region, pos.x - component.getPivotX(), pos.y - component.getPivotY());

        } catch (Exception e) {
            Gdx.app.log("DEBUG", "invalid renderSprite[" + object.getName() + "]");
        }
    }

    public static void renderScenery(SpriteBatch batch, GameObject object, OrthoCameraWrapper camera) {
        try {
            SceneryComponent scenery = object.getComponent(SceneryComponent.class);
            StaticTextureAsset asset = scenery.getAsset();

            Texture texture = asset.getTexture();
            int texWidth = texture.getWidth();

            Vector2 renderPos = new Vector2(object.getTransform());
            if (camera.getPosition().x > renderPos.x + texWidth/2.0f) {
                object.getTransform().x += texWidth;
                renderPos.x += texWidth;
            }

            renderPos.x -= texWidth;

            for (int i = 0; i < 3; i++) {

                batch.draw(texture, renderPos.x - asset.getPivotX(), renderPos.y - asset.getPivotY());

                ////Gdx.app.log("DEBUG", "scenery render pos" + renderPos + object.getTransform());
                renderPos.x += texWidth;
            }
        }
        catch (Exception e) {
            Gdx.app.log("DEBUG", "invalid renderScenery[" + object.getName() + "]");
        }
    }
}
