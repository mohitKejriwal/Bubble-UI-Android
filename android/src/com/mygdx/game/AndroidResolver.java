package com.mygdx.game;


import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by mohit on 11/10/2017.
 */

public class AndroidResolver implements AccessActivity {
    android.os.Handler handler;
    Context context;
    MainActivity mainActivity;

    public AndroidResolver(Context context) {
        handler = new Handler();
        this.context = context;
        mainActivity = (MainActivity) context;

    }

    @Override
    public void showToast(final String text) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void showCount(final ArrayList<String> brandSelect) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                mainActivity.setBrands(brandSelect);
                int selection = 5 - brandSelect.size();
                if (selection > 0) {
                    mainActivity.fabSubmit.setVisibility(View.GONE);
                    mainActivity.count.setText("Select your top " + String.valueOf(selection) + " favourite brands");
                } else {
                    mainActivity.fabSubmit.setVisibility(View.VISIBLE);
                    mainActivity.count.setText("You have selected 5 brands");
                }
            }
        });

    }


}
