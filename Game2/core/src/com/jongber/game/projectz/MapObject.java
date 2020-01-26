package com.jongber.game.projectz;

import com.jongber.game.core.GameObject;
import com.jongber.game.projectz.component.MapProperty;

public class MapObject {
    public static GameObject createMap(String mapName) {
        GameObject object = new GameObject();
        object.name = mapName;
        object.addComponent(new MapProperty(mapName));

        return object;
    }

}
