#include <jni.h>
#include <string>
#include <android/bitmap.h>

#define BRIGHNESS 0.2f
#define CONSTRAT 0.2f

extern "C"
JNIEXPORT jintArray JNICALL
Java_com_fast_flyer_ndk_image_ImageUtils_getImage(JNIEnv *env, jclass type, jintArray buffer_,
                                                  jint width, jint height) {

    jint *buffer = env->GetIntArrayElements(buffer_, NULL);

    int newSize = width * height;


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

            int color = buffer[width * j + i];

            // 以下的这些转换是怎么来的呢？
            // 查看java的Color转换源码可以发现
            r = (color >> 16) & 0xFF;
            g = (color >> 8) & 0xFF;
            b = color & 0xFF;
          //  a = color >>> 24;

            a = (color >> 24) & 0xFF;

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

            int newColor = (a << 24) | (r << 16) | (g << 8) | b;

            buffer[width * j + i] = newColor;
        }
    }

    jintArray result = env->NewIntArray(newSize);
    env->SetIntArrayRegion(result,0,newSize,buffer);
    env->ReleaseIntArrayElements(buffer_, buffer, 0);

    return result;
}