package com.mygdx.game;


import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * Created by mohit on 11/10/2017.
 */

public class AndroidResolver implements AccessActivity {
    android.os.Handler handler;
    Context context;


    public AndroidResolver(Context context) {
        handler = new Handler();
        this.context = context;
    }

    @Override
    public void showToast(final String text) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                MainActivity m = (MainActivity) context;
                m.count.setText(text);
            }
        });
    }


}
