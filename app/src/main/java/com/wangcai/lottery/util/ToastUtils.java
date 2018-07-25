package com.wangcai.lottery.util;

import android.content.Context;
import android.widget.Toast;

import static android.widget.Toast.makeText;

/**
 * Created by Sakura on 2016/9/8.
 */

public class ToastUtils
{
    public static void showLongToast(Context context, String text)
    {
        if (context != null)
        {
            makeText(context, text, Toast.LENGTH_LONG).show();
        }
    }

    public static void showShortToast(Context context, String text)
    {
        if (context != null)
        {
            makeText(context, text, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showLongToast(Context context, int resId)
    {
        if (context != null)
        {
            makeText(context, resId, Toast.LENGTH_LONG).show();
        }
    }

    public static void showShortToast(Context context, int resId)
    {
        if (context != null)
        {
            makeText(context, resId, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showShortToastLocation(Context context, String text, int location, int xOffset, int yOffset)
    {
        if (context != null)
        {
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast.setGravity(location, xOffset, yOffset);
            toast.show();
        }
    }
}
