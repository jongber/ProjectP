package com.jongber.game.desktop.room.event;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.core.event.GameEvent;
import com.jongber.game.desktop.room.component.PropProperty;

public class AddPropEvent extends GameEvent {

    private GameLayer layer;
    private String roomName;
    private String texturePath;

    public AddPropEvent(GameLayer layer, String roomName, String texturePath) {
        this.layer = layer;
        this.roomName = roomName;
        this.texturePath = texturePath;
    }

    @Override
    public void handle() {
        Texture texture = AssetManager.getTexture(this.texturePath);
        TextureRegion region = new TextureRegion(texture);
        PropProperty p = new PropProperty(region);

        GameObject object = new GameObject();
        object.addComponent(p);

        this.layer.addObject(this.roomName, object);
    }
}
