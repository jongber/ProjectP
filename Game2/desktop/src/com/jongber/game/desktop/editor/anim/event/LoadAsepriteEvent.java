package com.jongber.game.desktop.editor.anim.event;

import com.badlogic.gdx.graphics.Texture;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.core.event.GameEvent;
import com.jongber.game.desktop.editor.anim.AnimEditViewer;
import com.jongber.game.desktop.editor.anim.AsepriteJson;

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

        System.out.println(t);
    }
}
