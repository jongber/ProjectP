package com.jongber.game.desktop.room.event;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.core.event.GameEvent;
import com.jongber.game.desktop.room.component.PropProperty;

public class AddPropEvent extends GameEvent {

    private final GameLayer layer;
    private final String roomName;
    private final String texturePath;

    public GameObject created;

    public AddPropEvent(GameLayer layer, String roomName, String texturePath, GameEvent.Callback callback) {
        super(callback);
        this.layer = layer;
        this.roomName = roomName;
        this.texturePath = texturePath;
    }

    @Override
    public void handle() {
        Texture texture = AssetManager.getTexture(this.texturePath);
        TextureRegion region = new TextureRegion(texture);
        PropProperty p = new PropProperty(region);

        created = new GameObject();
        created.addComponent(p);

        this.layer.addObject(this.roomName, created);
    }
}
