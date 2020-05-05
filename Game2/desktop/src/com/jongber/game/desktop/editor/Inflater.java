package com.jongber.game.desktop.editor;

import com.badlogic.gdx.Gdx;
import com.jongber.game.desktop.common.CallbackEvent;
import com.jongber.game.desktop.editor.character.CharacterCmd;
import com.jongber.game.desktop.editor.character.CharacterView;
import com.jongber.game.desktop.editor.main.MainMenuCmd;
import com.jongber.game.desktop.editor.main.MainMenuView;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

public class Inflater {

    private static EditorApp app;
    private static HashMap<Class, Class> inflateMap = new HashMap<>();

    static {
        inflateMap.put(MainMenuCmd.class, MainMenuView.class);
        inflateMap.put(CharacterCmd.class, CharacterView.class);
    }

    public static void init(EditorApp app) {
        Inflater.app = app;
    }

    public static void returnToMain() {
        Inflater.inflate(MainMenuCmd.class);
    }

    public static void inflate(Class cmdClass) {
        if (inflateMap.containsKey(cmdClass)) {
            Class viewClass = inflateMap.get(cmdClass);

            app.post(new CallbackEvent(new CallbackEvent.Callback() {
                @Override
                public void invoke() {
                    try {
                        EditorView view = (EditorView) viewClass.newInstance();
                        EditorCmd cmd = (EditorCmd) cmdClass.newInstance();

                        Inflater.initComponent(cmd, view);

                        Inflater.validateApp(cmd, view);


                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }));
        }
        else {
            Gdx.app.debug("Error", "invalid inflate class name " + cmdClass.getName());
        }
    }

    private static void initComponent(EditorCmd cmd, EditorView view) {
        cmd.create(view);
        view.create(cmd);

        cmd.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                Gdx.app.exit();
            }
        });

        cmd.setVisible(true);
    }

    private static void validateApp(EditorCmd cmd, EditorView view) {
        app.dispose();
        app.setLayer(view);
        app.setCmd(cmd);
        app.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
}
