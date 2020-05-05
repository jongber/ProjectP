package com.jongber.game.desktop.old.editor.map.event;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.core.component.TextureComponent;
import com.jongber.game.core.event.GameEvent;

public class AddTextureEvent extends GameEvent {

    public interface Callback {
        void callback(AddTextureEvent event, GameObject created);
    }

    public String texturePath;
    private GameLayer layer;
    private Callback callback;
    private String parent;
    private String name;

    public AddTextureEvent(GameLayer layer, String texturePath) {
        this(layer, texturePath, null, "", null);
    }

    public AddTextureEvent(GameLayer layer, String texturePath, Callback callback) {
        this(layer, texturePath, null, "", callback);
    }

    public AddTextureEvent(GameLayer layer,
                           String texturePath,
                           String parent,
                           String name,
                           Callback callback) {
        this.layer = layer;
        this.callback = callback;
        this.parent = parent;
        this.name = name;
        this.texturePath = texturePath;
    }

    @Override
    public void handle() {
        GameObject object = new GameObject(this.name);
        Texture t = AssetManager.getTexture(this.texturePath);
        object.addComponent(new TextureComponent(new TextureRegion(t)));

        this.layer.addObject(this.parent, object);

        if (this.callback != null) {
            this.callback.callback(this, object);
        }
    }
}
