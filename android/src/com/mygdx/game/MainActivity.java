package com.mygdx.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements AndroidFragmentApplication.Callbacks {

    Bitmap bitmap;
    FloatingActionButton fabSubmit;
    Bundle bundle;
    File tempFile;
    int fileName = 0;
    String cachePath;
    TextView count;
    ArrayList<String> brandsID;
    ArrayList<String> colorList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabSubmit = (FloatingActionButton) findViewById(R.id.fabSubmit);
        count = (TextView) findViewById(R.id.tvBrandCount);

        cachePath = getCacheDir().getAbsolutePath();

        setColors();
        bundle = new Bundle();
        bundle.putStringArrayList("colorList", colorList);
        bundle.putString("cachePath", cachePath);
        setLogos();


        fabSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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

    public void setLogos() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://wwwns.akamai.com/media_resources/globe_emea.png")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                bitmap = BitmapFactory.decodeStream(response.body().byteStream()); // Read the data from the stream
                for (; fileName < colorList.size(); fileName++) {
                    tempFile = new File(getCacheDir(), "brand" + fileName);

                    tempFile.deleteOnExit();
                    FileOutputStream out = new FileOutputStream(tempFile);


                    try {
                        out = new FileOutputStream(tempFile);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                        // PNG is a lossless format, the compression factor (100) is ignored
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (out != null) {
                                out.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                AndroidLauncher.GameFragment fragment = new AndroidLauncher.GameFragment();
                fragment.setArguments(bundle);
                FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                trans.replace(R.id.fragment, fragment);
                trans.commit();

            }


        });
    }


    public void setBrands(ArrayList<String> brandsSelect) {
        brandsID = brandsSelect;
    }
}
