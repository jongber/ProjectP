package com.jongber.game.desktop.editor.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Align;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.component.TextComponent;
import com.jongber.game.core.controller.TextController;
import com.jongber.game.desktop.common.controller.BlockGridRenderer;
import com.jongber.game.desktop.editor.EditorCmd;
import com.jongber.game.desktop.editor.EditorView;

public class DialogView extends EditorView {

    @Override
    public void create(EditorCmd cmd) {
        GameObject object = this.create(" 아asdf임 ");

        this.getLayer(LayerType.Dialog).addObject(object);

        this.registerController(LayerType.Dialog, new TextController());
        //this.registerController(LayerType.Dialog, new BlockGridRenderer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }

    private GameObject create(String text) {
        GameObject object = new GameObject();
        TextComponent c = new TextComponent(text, null);
        c.align = Align.left;
        object.addComponent(c);

        return object;
    }
}
