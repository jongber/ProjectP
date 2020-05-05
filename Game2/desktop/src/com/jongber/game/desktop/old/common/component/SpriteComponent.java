package com.jongber.game.desktop.old.common.component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.jongber.game.core.asset.AnimationAsset;
import com.jongber.game.core.component.Component;
import com.jongber.game.core.graphics.VFAnimation;
import com.jongber.game.core.util.Tuple2;
import com.jongber.game.desktop.common.json.AsepriteJson;
import com.jongber.game.desktop.old.common.json.SpriteJson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpriteComponent extends Component {

    public static class AnimData {
        public AnimationAsset asset;
        public Vector2 pivot = new Vector2();

        public AnimData(AnimationAsset asset) {
            this.asset = asset;
        }

        public AnimData(AnimationAsset asset, Vector2 pivot) {
            this.asset = asset;
            this.pivot = pivot;
        }
    }

    Texture texture;

    public List<Integer> totalDurations = new ArrayList<>();
    public List<TextureRegion> totalImages = new ArrayList<>();
    public Map<String, AnimData> assetMap = new HashMap<>();

    public VFAnimation currentAnimation;

    public SpriteComponent(SpriteJson json, Texture t) {
        this.texture = t;
        for (Tuple2<Rectangle, Integer> item : json.frames) {
            Rectangle r = item.getItem1();
            TextureRegion region = new TextureRegion(t);
            region.setRegion((int)r.x, (int)r.y, (int)r.width, (int)r.height);
            totalImages.add(region);
            totalDurations.add(item.getItem2());
        }

        AnimationAsset asset = new AnimationAsset(json.name, this.totalImages, this.totalDurations);
        this.assetMap.put(asset.getName(), new AnimData(asset, json.pivot));

        this.currentAnimation = new VFAnimation(asset, VFAnimation.PlayMode.LOOP);
    }

    public SpriteComponent(AsepriteJson json, Texture texture) {
        this.texture = texture;

        for (AsepriteJson.Frame f : json.frames) {
            TextureRegion region = new TextureRegion(texture);
            region.setRegion(f.frame.x, f.frame.y, f.frame.w, f.frame.h);
            totalImages.add(region);

            totalDurations.add(f.duration);
        }

        for (AsepriteJson.FrameTag tag : json.meta.frameTags) {

            TextureRegion[] animImages = new TextureRegion[tag.to - tag.from + 1];
            List<Integer> animDurations = new ArrayList<>();

            for (int i = 0; i < animImages.length; ++i) {
                animImages[i] = this.totalImages.get(i + tag.from);
                animDurations.add(totalDurations.get(i + tag.from));
            }

            AnimationAsset asset = new AnimationAsset(tag.name, animImages, animDurations);
            this.assetMap.put(asset.getName(), new AnimData(asset));

            if (tag == json.meta.frameTags.get(0)) {
                this.currentAnimation = new VFAnimation(asset, VFAnimation.PlayMode.LOOP);
            }
        }
    }
}
