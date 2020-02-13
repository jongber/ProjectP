package com.jongber.game.desktop.editor.sprite.event;

import com.jongber.game.core.GameObject;
import com.jongber.game.core.asset.AnimationAsset;
import com.jongber.game.core.event.GameEvent;
import com.jongber.game.desktop.editor.sprite.component.SpriteComponent;

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
        SpriteComponent.AnimData data = new SpriteComponent.AnimData(asset);

        SpriteComponent c = this.object.getComponent(SpriteComponent.class);
        c.assetMap.put(name, data);
    }
}
