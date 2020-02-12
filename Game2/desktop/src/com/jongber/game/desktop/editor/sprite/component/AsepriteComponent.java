package com.jongber.game.desktop.editor.sprite.component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.jongber.game.core.asset.AnimationAsset;
import com.jongber.game.core.component.Component;
import com.jongber.game.core.graphics.VFAnimation;
import com.jongber.game.desktop.editor.sprite.AsepriteJson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AsepriteComponent extends Component {

    public static class AnimData {
        public AnimationAsset asset;
        public Vector2 pivot = new Vector2();

        public AnimData(AnimationAsset asset) {
            this.asset = asset;
        }
    }

    Texture texture;
    AsepriteJson json;

    public List<TextureRegion> totalImages = new ArrayList<>();
    public Map<String, AnimData> assetMap = new HashMap<>();

    public VFAnimation currentAnimation;

    public AsepriteComponent(AsepriteJson json, Texture texture) {
        this.json = json;
        this.texture = texture;

        List<Integer> totalDurations = new ArrayList<>();
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
