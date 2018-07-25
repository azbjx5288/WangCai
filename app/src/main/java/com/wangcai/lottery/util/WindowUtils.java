package com.wangcai.lottery.util;

import android.app.Activity;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.wangcai.lottery.R;
import com.wangcai.lottery.base.SystemBarTintManager;

/**
 * Created by Sakura on 2016/8/24.
 */

public class WindowUtils {
    private static final String TAG = "WindowUtils";

    /**
     * 将4.4系统以上的状态栏设置为半透明
     *
     * @param activity
     */
    public static void makeWindowTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            activity.setTheme(R.style.StatusBarTheme);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager systemBarTintManager = new SystemBarTintManager(activity);
            systemBarTintManager.setStatusBarTintEnabled(true);
            systemBarTintManager.setStatusBarTintResource(R.color.app_main);
        }
    }

    /**
     * 全屏隐藏虚拟键盘，让虚拟键盘一直不显示
     *
     * @param activity
     */
    public static void hideSystemUI(Activity activity) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
        window.setAttributes(params);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static float getDensity(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
        Log.e(TAG, "getDensity: " + density);
        return density;
    }

    public static int getWidth(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int widthPixels = metric.widthPixels;
        Log.e(TAG, "getWidth: " + widthPixels);
        return widthPixels;
    }

    public static int getHeight(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int heightPixels = metric.widthPixels;
        Log.e(TAG, "getHeight: " + heightPixels);
        return heightPixels;
    }
}
