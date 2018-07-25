package com.wangcai.lottery.pattern;

import android.graphics.Bitmap;

/**
 * Created by ACE-PC on 2017/3/22.
 *
 */

public class ClipImageUtils {
    /**
     * 对源位图进行剪裁
     *
     * @param source
     * @param x
     * @param y
     * @param width    剪裁内容的宽度
     * @param height   剪裁内容的高度
     * @param imWidth
     * @param imHeight
     * @return
     */
    public static Bitmap clipImage(Bitmap source, int x, int y, int width, int height, int imWidth, int imHeight) {
        int bmWidth = source.getWidth();
        int bmHeight = source.getHeight();
        float scale = Math.min((float) bmWidth / imWidth, (float) bmHeight / imHeight);
        return clipImage(source, x, y, width, height, scale);
    }

    /**
     * 对源位图进行剪裁
     *
     * @param source
     * @param x
     * @param y
     * @param width  剪裁内容的宽度
     * @param height 剪裁内容的高度
     * @param scale  剪裁比例
     * @return
     */
    public static Bitmap clipImage(Bitmap source, int x, int y, int width, int height, float scale) {

        int bmWidth = source.getWidth();
        int bmHeight = source.getHeight();

        x *= scale;
        y *= scale;
        width *= scale;
        height *= scale;

        /** 校正x,y的值 */
        x = (x + width > bmWidth) ? bmWidth - width : x;
        x = x < 0 ? 0 : x;
        y = (y + height > bmHeight) ? bmHeight - height : y;
        y = y < 0 ? 0 : y;

        return Bitmap.createBitmap(source, x, y, width, height);
    }
}
