package com.mygdx.game;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;

import java.util.ArrayList;

public class AndroidLauncher extends FragmentActivity implements AndroidFragmentApplication.Callbacks {
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


    }

	@Override
	public void exit() {

	}



	// 4. Create a Class that extends AndroidFragmentApplication which is the Fragment implementation for Libgdx.
	public static class GameFragment extends AndroidFragmentApplication {
		// 5. Add the initializeForView() code in the Fragment's onCreateView method.
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			ArrayList<String> array = this.getArguments().getStringArrayList("colorList");
            //byte[] arrayLogo = this.getArguments().getByteArray("logos");
            return initializeForView(new MyGdxGame(array));

        }
    }



}

