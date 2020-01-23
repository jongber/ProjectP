package com.jongber.game.editor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.core.asset.TextureAsset;
import com.jongber.game.core.component.TextureComponent;
import com.jongber.game.editor.component.TextComponent;

public class ObjectFactory {

    public static GameObject createTextBox(String name, Vector2 pos) {
        GameObject object = new GameObject();
        object.name = name;

        TextComponent component = new TextComponent(name);
        component.pos = pos;
        object.addComponent(component);

        return object;
    }

//    public static GameObject createTexture() {
//        GameObject object = new GameObject();
//
//        TextureAsset asset = AssetManager.getTextureAsset("badlogic.jpg");
//        TextureRegion region = new TextureRegion(asset.getTexture());
//
//        object.addComponent(new TextureComponent(region));
//
//        return object;
//    }
}
