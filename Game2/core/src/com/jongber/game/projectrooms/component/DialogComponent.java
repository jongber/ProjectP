package com.jongber.game.projectrooms.component;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.CharArray;
import com.badlogic.gdx.utils.StringBuilder;
import com.jongber.game.core.component.Component;
import com.jongber.game.projectrooms.MainLayer;


public class DialogComponent extends Component {

    public String text;

    public GlyphLayout layout = new GlyphLayout();

    public DialogComponent(String text) {
        this.set(text);
    }

    private float elapsed = 0.0f;
    private int curIndex = 0;
    private StringBuilder curString = new StringBuilder();

    public DialogComponent set(String text) {
        this.text = text;
        curString.append(this.text.charAt(curIndex));

        return this;
    }

    public GlyphLayout getLayout(float elapsed) {
        calcIndex(elapsed);

        return this.layout;
    }

    private int calcIndex(float elapsed) {
        this.elapsed += elapsed;

        if (this.elapsed >= 0.1f) {
            this.elapsed = 0.0f;
            curIndex++;

            if (curIndex >= this.text.length()) {
                curIndex = this.text.length() - 1;
            }
            else {
                curString.append(this.text.charAt(curIndex));

                BitmapFont font = MainLayer.assets.getFont();
                layout.setText(font, curString, Color.WHITE, 400, Align.left, true);
            }
        }

        return curIndex;
    }
}