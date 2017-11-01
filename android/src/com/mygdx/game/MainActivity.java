package com.mygdx.game;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;

public class MainActivity extends AppCompatActivity implements AndroidFragmentApplication.Callbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Intent i = new Intent(this, AndroidLauncher.class);
        startActivity(i);*/


        AndroidLauncher.GameFragment fragment = new AndroidLauncher.GameFragment();
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.fragment, fragment);
        trans.commit();
    }

    @Override
    public void exit() {

    }
}
