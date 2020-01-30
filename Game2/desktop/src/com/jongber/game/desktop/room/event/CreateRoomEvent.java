package com.jongber.game.desktop.room.event;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.core.component.TextureComponent;
import com.jongber.game.core.event.GameEvent;

public class CreateRoomEvent extends GameEvent {

    private GameLayer layer;

    public CreateRoomEvent(GameLayer layer) {
        this.layer = layer;
    }

    @Override
    public void handle() {
        this.layer.addObject(create());
    }

    private GameObject create() {
        GameObject object = new GameObject();

        Texture texture = AssetManager.getTexture("projectz/house/wallpaper/3x3_1.png");
        object.addComponent(new TextureComponent(new TextureRegion(texture)));

        return object;
    }
}
