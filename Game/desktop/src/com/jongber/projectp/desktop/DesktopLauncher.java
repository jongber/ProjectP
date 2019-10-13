package com.jongber.projectp.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jongber.projectp.MyGdxGame;
import com.jongber.projectp.test.CameraTest;
import com.jongber.projectp.test.JsonLoad;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1920;
		config.height = 1080;
		new LwjglApplication(new CameraTest(), config);
	}
}
