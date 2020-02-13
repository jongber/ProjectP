package com.jongber.game.desktop.editor.sprite.event;

import com.badlogic.gdx.graphics.Texture;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.core.event.GameEvent;
import com.jongber.game.desktop.editor.sprite.SpriteEditViewer;
import com.jongber.game.desktop.editor.sprite.AsepriteJson;
import com.jongber.game.desktop.editor.sprite.component.AsepriteComponent;

public class LoadAsepriteEvent extends GameEvent {

    public interface Callback {
        void callback(GameObject created);
    }

    SpriteEditViewer viewer;
    String path;
    AsepriteJson json;
    Callback callback;

    public LoadAsepriteEvent(SpriteEditViewer viewer,
                             String path,
                             AsepriteJson json,
                             Callback callback) {
        this.viewer = viewer;
        this.path = path;
        this.json = json;
        this.callback = callback;
    }

    @Override
    public void handle() {
        Texture t = AssetManager.getTexture(this.path);

        AsepriteComponent c = new AsepriteComponent(this.json, t);
        GameObject o = new GameObject();
        o.addComponent(c);

        this.viewer.getLayer().addObject(o);

        this.callback.callback(o);
    }
}
