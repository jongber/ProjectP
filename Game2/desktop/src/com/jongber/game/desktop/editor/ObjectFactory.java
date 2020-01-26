package com.jongber.game.desktop.editor;

import com.badlogic.gdx.math.Vector2;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.component.TextComponent;

public class ObjectFactory {

    public static GameObject createButton(String name, Vector2 pos, TextComponent.ClickListener listener) {
        GameObject object = new GameObject();
        object.name = "button";
        object.transform.local.setToTranslation(pos.x, pos.y);

        TextComponent comp = new TextComponent(name, listener);
        object.addComponent(comp);

        return object;
    }
}
