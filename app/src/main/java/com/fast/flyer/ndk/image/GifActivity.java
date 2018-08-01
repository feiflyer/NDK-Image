package com.fast.flyer.ndk.image;

import android.graphics.Bitmap;
import android.support.rastermill.FrameSequence;
import android.support.rastermill.FrameSequenceDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.InputStream;
import java.util.HashSet;

public class GifActivity extends AppCompatActivity implements View.OnClickListener {

    Button start, stop, vis, invis, circle_mask, gif, webp;

    View drawableView;

    FrameSequenceDrawable mDrawable;
    int mResourceId;

    @Override
    public void onClick(View v) {
        if (v == start) {
            mDrawable.start();
        } else if (v == stop) {
            mDrawable.stop();
        } else if (v == vis) {
            mDrawable.setVisible(true, true);
        } else if (v == invis) {
            mDrawable.setVisible(false, true);
        } else if (v == circle_mask) {
            mDrawable.setCircleMaskEnabled(!mDrawable.getCircleMaskEnabled());
        } else if (v == gif) {
            mResourceId = R.raw.animated_gif;
            showAnimated();
        } else if (v == webp) {
            mResourceId = R.raw.animated_webp;
            showAnimated();
        }
    }

    // This provider is entirely unnecessary, just here to validate the acquire/release process
    private class CheckingProvider implements FrameSequenceDrawable.BitmapProvider {
        HashSet<Bitmap> mBitmaps = new HashSet<Bitmap>();

        @Override
        public Bitmap acquireBitmap(int minWidth, int minHeight) {
            Bitmap bitmap =
                    Bitmap.createBitmap(minWidth + 1, minHeight + 4, Bitmap.Config.ARGB_8888);
            mBitmaps.add(bitmap);
            return bitmap;
        }

        @Override
        public void releaseBitmap(Bitmap bitmap) {
            if (!mBitmaps.contains(bitmap)) throw new IllegalStateException();
            mBitmaps.remove(bitmap);
            bitmap.recycle();
        }

        public boolean isEmpty() {
            return mBitmaps.isEmpty();
        }
    }

    final CheckingProvider mProvider = new CheckingProvider();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_gif);

        drawableView = findViewById(R.id.drawableview);

        start = findViewById(R.id.start);
        start.setOnClickListener(this);

        stop = findViewById(R.id.stop);
        stop.setOnClickListener(this);

        vis = findViewById(R.id.vis);
        vis.setOnClickListener(this);

        invis = findViewById(R.id.invis);
        invis.setOnClickListener(this);

        circle_mask = findViewById(R.id.circle_mask);
        circle_mask.setOnClickListener(this);

        gif = findViewById(R.id.gif);
        gif.setOnClickListener(this);

        webp = findViewById(R.id.webp);
        webp.setOnClickListener(this);


        mResourceId = R.raw.animated_gif;


    }

    @Override
    protected void onResume() {
        super.onResume();
        showAnimated();
    }

    private void showAnimated() {

        destroyAnimated();

        InputStream is = getResources().openRawResource(mResourceId);
        FrameSequence fs = FrameSequence.decodeStream(is);
        mDrawable = new FrameSequenceDrawable(fs, mProvider);
        mDrawable.setOnFinishedListener(new FrameSequenceDrawable.OnFinishedListener() {
            @Override
            public void onFinished(FrameSequenceDrawable drawable) {
                Toast.makeText(getApplicationContext(),
                        "The animation has finished", Toast.LENGTH_SHORT).show();
            }
        });
        drawableView.setBackgroundDrawable(mDrawable);
    }

    private void destroyAnimated() {
        if (null != mDrawable) {
            mDrawable.destroy();
            if (!mProvider.isEmpty()) throw new IllegalStateException("All bitmaps not recycled");
            mDrawable = null;
            drawableView.setBackgroundDrawable(null);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        destroyAnimated();
    }
}

