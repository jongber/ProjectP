package com.jongber.projectp.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jongber.projectp.MyGdxGame;
import com.jongber.projectp.TestSpriteAnim;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.title = "camera";


        config.width = 1280;

        config.height = 720;
		//new LwjglApplication(new MyGdxGame(), config);
		new LwjglApplication(new TestSpriteAnim(), config);
	}
}
