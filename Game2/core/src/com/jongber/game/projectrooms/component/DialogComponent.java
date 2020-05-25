package com.jongber.game.projectrooms.component;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.jongber.game.core.component.Component;
import com.jongber.game.projectrooms.MainLayer;


public class DialogComponent extends Component {

    public String text;

    public GlyphLayout layout = new GlyphLayout();

    public DialogComponent(String text) {
        this.set(text);
    }

    public DialogComponent set(String text) {
        this.text = text;

        BitmapFont font = MainLayer.assets.getFont();

        layout.setText(font, text, Color.WHITE, 300, Align.left, true);

        return this;
    }
}