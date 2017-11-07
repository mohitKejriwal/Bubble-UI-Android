package com.mygdx.game;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AndroidFragmentApplication.Callbacks {

    Bitmap bitmap;
    //ArrayList<byte[]> logos = new ArrayList<>();
    byte[] byteLogo;
    Bundle bundle;
    ArrayList<String> colorList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setColors();

        bundle = new Bundle();
        bundle.putStringArrayList("colorList", colorList);
        bundle.putByteArray("logos", byteLogo);
        AndroidLauncher.GameFragment fragment = new AndroidLauncher.GameFragment();
        fragment.setArguments(bundle);
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.fragment, fragment);
        trans.commit();

    }

    @Override
    public void exit() {

    }

    public void setColors() {
        colorList.add("#AB47BC");
        colorList.add("#F44336");
        colorList.add("#EC407A");
        colorList.add("#7E57C2");
        colorList.add("#3F51B5");
        colorList.add("#2196F3");
        colorList.add("#26C6DA");
        colorList.add("#26A69A");
        colorList.add("#4CAF50");
        colorList.add("#FFA000");

        colorList.add("#AB47BC");
        colorList.add("#F44336");
        colorList.add("#EC407A");
        colorList.add("#7E57C2");
        colorList.add("#3F51B5");
        colorList.add("#2196F3");
        colorList.add("#26C6DA");
        colorList.add("#26A69A");
        colorList.add("#4CAF50");
        colorList.add("#FFA000");

    }


}
