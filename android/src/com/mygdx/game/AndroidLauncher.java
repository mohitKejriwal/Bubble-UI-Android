package com.mygdx.game;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	MyGdxGame game;
	AndroidApplicationConfiguration config;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		config = new AndroidApplicationConfiguration();
		game = new MyGdxGame();
		initialize(game, config);
	}


	@Override
	public void onLowMemory() {
		super.onLowMemory();
		game.dispose();
		game = new MyGdxGame();
		initialize(game, config);
	}

}

