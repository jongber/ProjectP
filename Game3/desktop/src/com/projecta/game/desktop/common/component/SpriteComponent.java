package com.projecta.game.desktop.common.component;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.projecta.game.core.base.object.GameObjectComponent;
import com.projecta.game.core.util.Tuple2;

import java.util.ArrayList;

public class SpriteComponent extends GameObjectComponent {
    public ArrayList<Tuple2<TextureRegion, Integer>> frames = new ArrayList<>();
    public int currentFrame = 0;
    public int elapsedTime = 0;

    public void addFrame(TextureRegion r, int frameInterval) {
        this.frames.add(new Tuple2<>(r, frameInterval));
    }
}
