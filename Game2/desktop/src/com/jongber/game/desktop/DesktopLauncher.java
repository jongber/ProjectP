package com.jongber.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.utils.I18NBundle;
import com.jongber.game.desktop.editor.EditorApp;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = true;
		config.width = 1600;
		config.height = 900;
		LwjglApplication app = new LwjglApplication(new EditorApp(), config);
	}
}
