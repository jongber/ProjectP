package com.jongber.game.editor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.core.asset.TextureAsset;
import com.jongber.game.core.component.TextureComponent;
import com.jongber.game.editor.component.TextComponent;

public class ObjectFactory {
    public static GameObject createLabel(String name) {
        GameObject object = new GameObject();
        object.name = name;

        TextureAsset asset = AssetManager.getTextureAsset("badlogic.jpg");
        TextureRegion region = new TextureRegion(asset.getTexture());

        object.addComponent(new TextComponent(name));
        object.addComponent(new TextureComponent(region));

        return object;
    }

    public static GameObject createTexture() {
        GameObject object = new GameObject();

        TextureAsset asset = AssetManager.getTextureAsset("badlogic.jpg");
        TextureRegion region = new TextureRegion(asset.getTexture());

        object.addComponent(new TextureComponent(region));

        return object;
    }
}
