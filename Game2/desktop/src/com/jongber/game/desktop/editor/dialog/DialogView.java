package com.jongber.game.desktop.editor.dialog;

import com.jongber.game.core.GameObject;
import com.jongber.game.desktop.common.controller.BlockGridRenderer;
import com.jongber.game.desktop.editor.EditorCmd;
import com.jongber.game.desktop.editor.EditorView;
import com.jongber.game.projectrooms.MainLayer;
import com.jongber.game.projectrooms.component.DialogComponent;
import com.jongber.game.projectrooms.controller.DialogController;

public class DialogView extends EditorView {
    @Override
    public void create(EditorCmd cmd) {

        this.registerController(new DialogController());
        this.registerController(new BlockGridRenderer());

        String text = MainLayer.assets.getBundle().get("_test_long_text");

        for (int i = 0; i < 5000; ++i)
            this.addObject(create(text));
    }

    public GameObject create(String text) {
        GameObject object = new GameObject();

        DialogComponent c = new DialogComponent(text);
        object.addComponent(c);

        return object;
    }

}
