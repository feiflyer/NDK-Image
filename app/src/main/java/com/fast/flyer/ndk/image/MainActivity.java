package com.fast.flyer.ndk.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

private ImageView mImageView;
private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = findViewById(R.id.image_view);

        mBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.liuyan);

    }

    public void originBitmap(View view){
        mImageView.setImageBitmap(mBitmap);
    }

    public void javaHandle(View view){
        mImageView.setImageBitmap(ImageUtils.changeBitmap(mBitmap));
    }

    public void ndkHandle(View view){
        mImageView.setImageBitmap(ImageUtils.changeBitmapNDK(mBitmap));
    }

}
