package com.jongber.game.desktop.editor;

import com.badlogic.gdx.Gdx;
import com.jongber.game.desktop.common.CallbackEvent;
import com.jongber.game.desktop.editor.mainmenu.MainMenuCmd;
import com.jongber.game.desktop.editor.mainmenu.MainMenuView;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

public class Inflater {

    private static EditorApp app;
    private static HashMap<Class<? extends EditorCmd>, Class<? extends EditorView>> inflateMap = new HashMap<>();

    static {
        inflateMap.put(MainMenuCmd.class, MainMenuView.class);
    }

    public static void init(EditorApp app) {
        Inflater.app = app;
    }

    public static void returnToMain() {
        Inflater.inflate(MainMenuCmd.class);
    }

    public static void inflate(Class<? extends EditorCmd> cmdClass) {
        if (inflateMap.containsKey(cmdClass)) {
            Class<? extends EditorView> viewClass = inflateMap.get(cmdClass);

            app.post(new CallbackEvent(new CallbackEvent.Callback() {
                @Override
                public void invoke() {
                    try {
                        EditorView view = viewClass.newInstance();
                        EditorCmd cmd = cmdClass.newInstance();

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
