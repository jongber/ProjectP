package com.jongber.game.desktop.editor.dialog;

import com.jongber.game.core.GameObject;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.desktop.editor.EditorCmd;
import com.jongber.game.desktop.editor.EditorView;
import com.jongber.game.projectrooms.component.DialogComponent;
import com.jongber.game.projectrooms.controller.DialogController;

public class DialogView extends EditorView {

    @Override
    public void create(EditorCmd cmd) {
        String str = AssetManager.getBundle().get("_test_text");
        GameObject object = this.create(str);

        this.getLayer(LayerType.Dialog).addObject(object);

        this.registerController(LayerType.Dialog, new DialogController());
    }

    private GameObject create(String text) {
        GameObject object = new GameObject();
        DialogComponent c = new DialogComponent(text);
        object.addComponent(c);

        return object;
    }
}
