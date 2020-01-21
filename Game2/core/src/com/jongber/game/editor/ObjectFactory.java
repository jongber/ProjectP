package com.jongber.game.editor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.component.TextureComponent;

public class ObjectFactory {
    public static GameObject createLabel(String name) {
        GameObject object = new GameObject();
        object.name = name;

        TextureRegion region = new TextureRegion(new Texture("badlogic.jpg"));

        object.addComponent(new TextureComponent(region));

        return object;
    }
}
