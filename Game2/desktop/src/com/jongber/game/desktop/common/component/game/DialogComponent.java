package com.jongber.game.desktop.common.component.game;

import com.jongber.game.core.asset.FontManager;
import com.jongber.game.core.component.Component;

public class DialogComponent extends Component {
    public FontManager.TextBlock textBlock = new FontManager.TextBlock();

    public int _width;
    public int _height;

    public DialogComponent(String text) {
        this.set(text);
    }

    public DialogComponent set(String text) {
        textBlock.text = text;
        return this;
    }
}
