package com.jongber.game.core.component;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;
import com.jongber.game.core.asset.FontManager;

public class TextComponent extends Component {

    public interface ClickListener {
        void onClicked();
    }

    public FontManager.TextBlock textBlock = new FontManager.TextBlock();
    public float width = 0;
    public float height = 0;
    public int align = Align.left;
    public Color background = Color.CLEAR;
    private ClickListener listener;

    public TextComponent(String text, ClickListener listener) {
        this.textBlock.text = text;
        this.listener = listener;
    }

    public void onClick() {
        if (this.listener != null)
            this.listener.onClicked();
    }
}
