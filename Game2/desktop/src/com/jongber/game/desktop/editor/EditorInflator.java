package com.jongber.game.desktop.editor;

import com.badlogic.gdx.Gdx;
import com.jongber.game.desktop.common.event.CallbackEvent;
import com.jongber.game.desktop.editor.main.MainMenuView;

public class EditorInflator {

    private static EditorApp app;

    public static void init(EditorApp app) {
        EditorInflator.app = app;
    }

    public static void returnToMain() {
        app.post(new CallbackEvent(new CallbackEvent.Callback() {
            @Override
            public void invoke() {
                app.disposeLayer();
                app.setLayer(new MainMenuView());
                app.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            }
        }));
    }

    public static void inflateEditor() {

    }
}
