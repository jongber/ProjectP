package com.jongber.game.desktop.common.component;

import com.jongber.game.core.asset.AnimationAsset;
import com.jongber.game.core.component.Component;
import com.jongber.game.core.graphics.VFAnimation;

public class SpriteComponent extends Component {
    public AnimationAsset asset;
    public VFAnimation animation = new VFAnimation();

    public void set(AnimationAsset asset, VFAnimation.PlayMode mode) {
        this.asset = asset;
        this.animation.set(asset, mode);
    }
}
