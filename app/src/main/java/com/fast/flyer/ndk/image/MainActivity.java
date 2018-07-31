package com.fast.flyer.ndk.image;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mImageHandle, mImageGif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageGif = findViewById(R.id.image_gif);
        mImageHandle = findViewById(R.id.image_handle);

        mImageGif.setOnClickListener(this);
        mImageHandle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mImageHandle) {
            startActivity(new Intent(this, ImageActivity.class));
        }else if(v == mImageGif){
            startActivity(new Intent(this, GifActivity.class));
        }
    }
}
