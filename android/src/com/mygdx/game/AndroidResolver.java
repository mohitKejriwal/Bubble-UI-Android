package com.mygdx.game;


import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

/**
 * Created by mohit on 11/10/2017.
 */

public class AndroidResolver implements AccessActivity {
    android.os.Handler handler;
    Context context;
    MainActivity m;

    public AndroidResolver(Context context) {
        handler = new Handler();
        this.context = context;
        m = (MainActivity) context;

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
    public void showCount(final String text) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                int selection = 5 - Integer.parseInt(text);
                if (selection > 0) {
                    m.fabSubmit.setVisibility(View.GONE);
                    m.count.setText("Select your top " + String.valueOf(selection) + " favourite brands");
                } else {
                    m.fabSubmit.setVisibility(View.VISIBLE);
                    m.count.setText("You have selected 5 brands");
                }
            }
        });

    }


}
