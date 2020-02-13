package com.jongber.game.desktop.editor.sprite.event;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.asset.AnimationAsset;
import com.jongber.game.core.event.GameEvent;
import com.jongber.game.desktop.editor.sprite.component.AsepriteComponent;

import java.util.ArrayList;

public class AddSpriteEvent extends GameEvent {

    private GameObject object;
    private String name;

    public AddSpriteEvent(GameObject object, String name) {
        this.object = object;
        this.name = name;
    }

    @Override
    public void handle() {
        AnimationAsset asset = new AnimationAsset(this.name, new ArrayList<>(), new ArrayList<>());
        AsepriteComponent.AnimData data = new AsepriteComponent.AnimData(asset);

        AsepriteComponent c = this.object.getComponent(AsepriteComponent.class);
        c.assetMap.put(name, data);
    }
}
