package com.mygdx.game;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidFragmentApplication;

public class AndroidLauncher extends FragmentActivity implements AndroidFragmentApplication.Callbacks {
	AndroidApplicationConfiguration config;
	/*@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		config = new AndroidApplicationConfiguration();
		game = new MyGdxGame();
		initialize(game, config);
	}*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 6. Finally, replace the AndroidLauncher activity content with the Libgdx Fragment.
		GameFragment fragment = new GameFragment();
		FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
		trans.replace(android.R.id.content, fragment);
		trans.commit();
	}

	@Override
	public void exit() {

	}

/*
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		game = new MyGdxGame();
		return initializeForView(game);
	}
*/

	// 4. Create a Class that extends AndroidFragmentApplication which is the Fragment implementation for Libgdx.
	public static class GameFragment extends AndroidFragmentApplication {
		// 5. Add the initializeForView() code in the Fragment's onCreateView method.
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			return initializeForView(new MyGdxGame());
		}

		@Override
		public void onLowMemory() {
			super.onLowMemory();
			initializeForView(new MyGdxGame());
		}
	}
}

