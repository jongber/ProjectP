package com.jongber.game.desktop.old.editor.sprite.event;

import com.badlogic.gdx.graphics.Texture;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.core.event.GameEvent;
import com.jongber.game.desktop.old.editor.sprite.SpriteEditViewer;
import com.jongber.game.desktop.common.json.AsepriteJson;
import com.jongber.game.desktop.common.component.SpriteComponent;

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

        SpriteComponent c = new SpriteComponent(this.json, t);
        GameObject o = new GameObject();
        o.addComponent(c);

        this.viewer.getLayer().addObject(o);

        this.callback.callback(o);
    }
}
