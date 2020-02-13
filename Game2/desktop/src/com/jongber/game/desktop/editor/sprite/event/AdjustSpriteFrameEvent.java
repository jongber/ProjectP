package com.jongber.game.desktop.editor.sprite.event;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.asset.AnimationAsset;
import com.jongber.game.core.event.GameEvent;
import com.jongber.game.desktop.editor.sprite.component.AsepriteComponent;

import java.util.ArrayList;
import java.util.List;

public class AdjustSpriteFrameEvent extends GameEvent {

    private String name;
    private GameObject created;
    private int[] frames;

    public AdjustSpriteFrameEvent(GameObject created, String name, int[] frames) {
        this.created = created;
        this.frames = frames;
        this.name = name;
    }

    @Override
    public void handle() {
        AsepriteComponent c = this.created.getComponent(AsepriteComponent.class);

        List<Integer> durations = new ArrayList<>();
        List<TextureRegion> regions = new ArrayList<>();

        for (int i = 0; i < frames.length; ++i) {
            regions.add(c.totalImages.get(frames[i]));
            durations.add(c.totalDurations.get(frames[i]));
        }

        AnimationAsset asset = new AnimationAsset(name, regions, durations);
        c.assetMap.remove(name);
        c.assetMap.put(name, new AsepriteComponent.AnimData(asset));
    }
}
