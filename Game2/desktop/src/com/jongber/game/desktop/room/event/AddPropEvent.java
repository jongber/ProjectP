package com.jongber.game.desktop.room.event;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.core.event.GameEvent;
import com.jongber.game.desktop.viewer.component.PropProperty;

public class AddPropEvent extends GameEvent {

    public interface Callback {
        void callback(GameObject created);
    }

    private final GameLayer layer;
    private final String roomName;
    private final String texturePath;
    private final Vector2 pos = new Vector2();
    private Callback callback;

    public GameObject created;

    public AddPropEvent(GameLayer layer,
                        String roomName,
                        String texturePath,
                        Vector2 pos,
                        AddPropEvent.Callback callback) {
        this.pos.set(pos);
        this.layer = layer;
        this.roomName = roomName;
        this.texturePath = texturePath;
        this.callback = callback;
    }

    @Override
    public void handle() {
        Texture texture = AssetManager.getTexture(this.texturePath);
        TextureRegion region = new TextureRegion(texture);
        PropProperty p = new PropProperty(region);

        created = new GameObject();
        created.addComponent(p);
        created.transform.local.setToTranslation(this.pos);

        this.layer.addObject(this.roomName, created);

        this.callback.callback(created);
    }
}
