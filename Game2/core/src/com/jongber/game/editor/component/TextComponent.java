package com.jongber.game.editor.component;

import com.jongber.game.core.component.Component;

public class TextComponent extends Component {
    public final static int fontSize = 24;
    public String text;

    public TextComponent(String text) {
        this.text = text;
    }
}
