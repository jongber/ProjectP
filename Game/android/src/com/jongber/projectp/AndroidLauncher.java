package com.jongber.projectp;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.jongber.projectp.MyGdxGame;
import com.jongber.projectp.test.CameraTest;
import com.jongber.projectp.test.JsonLoad;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new CameraTest(), config);
	}
}
