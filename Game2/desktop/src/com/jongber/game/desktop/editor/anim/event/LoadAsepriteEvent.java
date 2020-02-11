package com.jongber.game.desktop.editor.anim.event;

import com.badlogic.gdx.graphics.Texture;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.core.event.GameEvent;
import com.jongber.game.desktop.editor.anim.AnimEditViewer;
import com.jongber.game.desktop.editor.anim.AsepriteJson;
import com.jongber.game.desktop.editor.anim.component.AsepriteComponent;

public class LoadAsepriteEvent extends GameEvent {

    AnimEditViewer viewer;
    String path;
    AsepriteJson json;

    public LoadAsepriteEvent(AnimEditViewer viewer, String path, AsepriteJson json) {
        this.viewer = viewer;
        this.path = path;
        this.json = json;
    }

    @Override
    public void handle() {
        Texture t = AssetManager.getTexture(this.path);

        AsepriteComponent c = new AsepriteComponent(this.json, t);
        GameObject o = new GameObject();
        o.addComponent(c);

        this.viewer.getLayer().addObject(o);
    }
}
