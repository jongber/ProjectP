package com.jongber.game.editor.component;

import com.jongber.game.common.FontManager.FontSize;

public class LabelComponent {
    public String text;
    public FontSize size;

    public LabelComponent(String text, FontSize size) {
        this.text = text;
        this.size = size;
    }
}
