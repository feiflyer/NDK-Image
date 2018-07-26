package com.fast.flyer.ndk.image;

import android.graphics.Bitmap;
import android.graphics.Color;

public class ImageUtils {

    private static final float BRIGHNESS = 0.2F;
    private static final float CONSTRAT = 0.2F;


    static {
        System.loadLibrary("native-lib");
    }


    public static Bitmap changeBitmap(Bitmap src) {
        int width = src.getWidth();
        int height = src.getHeight();

        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        int r, g, b, a;
        //亮度
        int bab = (int) (255 * BRIGHNESS);
        //对比度
        float ca = 1 + CONSTRAT;

        int cab = (int) (ca * 65536) + 1;

        //遍历所有的像素点

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                //每个像素点的三原色

                int color = src.getPixel(i, j);

                r = Color.green(color);
                g = Color.green(color);
                b = Color.blue(color);
                a = Color.alpha(color);

                // 美黑
                int ri = r - bab;
                int gi = g - bab;
                int bi = b - bab;
                int ai = a - bab;

                //边界检测
                r = ri > 255?255:(ri < 0 ? 0:ri);
                g = gi > 255?255:(gi < 0 ? 0:gi);
                b = bi > 255?255:(bi < 0 ? 0:bi);
                a = ai > 255?255:(ai < 0 ? 0:ai);


                //对比度进行变化

                ri = r - 128;
                gi = g - 128;
                bi = b - 128;
                ai = a - 128;

                ri = (ri * cab) >> 16;
                gi = (gi * cab) >> 16;
                bi = (bi * cab) >> 16;
                ai = (ai * cab) >> 16;

                ri = r + 128;
                gi = g + 128;
                bi = b + 128;
                ai = a + 128;

                //边界检测
                r = ri > 255?255:(ri < 0 ? 0:ri);
                g = gi > 255?255:(gi < 0 ? 0:gi);
                b = bi > 255?255:(bi < 0 ? 0:bi);
                a = ai > 255?255:(ai < 0 ? 0:ai);

                result.setPixel(i,j,Color.argb(a,r,g,b));


            }
        }

        return result;
    }

    public static Bitmap changeBitmapNDK(Bitmap src) {
        int width = src.getWidth();
        int height = src.getHeight();

        int buffer[] = new int[width * height];

        //将图像像素放进数组
        src.getPixels(buffer,0,width,1,1,width - 1, height - 1);

        int result[] = getImage(buffer,width,height);

        Bitmap resultBitmap = Bitmap.createBitmap(result,width, height, Bitmap.Config.ARGB_8888);

        return resultBitmap;
    }


    public static native int[] getImage(int buffer[], int width, int height);
}
