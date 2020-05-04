package com.jongber.game.desktop.editor.sprite.event;

import com.badlogic.gdx.graphics.Texture;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.core.event.GameEvent;
import com.jongber.game.desktop.editor.sprite.SpriteEditViewer;
import com.jongber.game.desktop.editor.sprite.component.SpriteComponent;
import com.jongber.game.projectz.json.SpriteJson;

public class LoadSpriteJsonEvent extends GameEvent {

    public interface Callback {
        void callback(GameObject created);
    }

    SpriteEditViewer viewer;
    SpriteJson json;
    Callback callback;

    public LoadSpriteJsonEvent(SpriteEditViewer viewer, SpriteJson json, Callback callback) {
        this.viewer = viewer;
        this.json = json;
        this.callback = callback;
    }

    @Override
    public void handle() {
        Texture t = AssetManager.getTexture(this.json.image);

        SpriteComponent c = new SpriteComponent(this.json, t);
        GameObject o = new GameObject();
        o.addComponent(c);

        this.viewer.getLayer().addObject(o);

        callback.callback(o);
    }
}