package com.jongber.game.desktop.editor.sprite.event;

import com.jongber.game.core.GameObject;
import com.jongber.game.core.event.GameEvent;
import com.jongber.game.core.graphics.VFAnimation;
import com.jongber.game.desktop.editor.sprite.component.AsepriteComponent;

public class ChangeSpriteEvent extends GameEvent {

    GameObject object;
    String name;

    public ChangeSpriteEvent(GameObject object, String name) {
        this.object = object;
        this.name = name;
    }

    @Override
    public void handle() {
        AsepriteComponent c = this.object.getComponent(AsepriteComponent.class);
        AsepriteComponent.AnimData asset = c.assetMap.get(name);
        c.currentAnimation = new VFAnimation(asset.asset, VFAnimation.PlayMode.LOOP);
    }
}
