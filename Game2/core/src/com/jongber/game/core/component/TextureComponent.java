package com.jongber.game.core.component;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class TextureComponent extends Component{

  public final TextureRegion region;
  public Vector2 pivot = new Vector2();

  public TextureComponent(TextureRegion region) {
    this(region, 0, 0);
  }

  public TextureComponent(TextureRegion region, float x, float y) {
    this.region = region;
    this.pivot.set(x, y);
  }
}
