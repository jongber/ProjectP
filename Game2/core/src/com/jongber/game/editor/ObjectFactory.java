package com.jongber.game.editor;

import com.jongber.game.core.GameObject;

public class ObjectFactory {
    public static GameObject createLabel(GameObject.Callback callback) {
        GameObject object = new GameObject(callback);

        return object;
    }
}
