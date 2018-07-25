package com.wangcai.lottery.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Sakura on 2016/9/12.
 */

public class SharedPreferencesUtils {
    private static final String spFileName = "welcomePage";
    public static final String FIRST_OPEN = "first_open";

    public static void putString(Context context, String fileName, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Activity.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).apply();
    }

    public static String getString(Context context, String fileName, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Activity.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static void putLong(Context context, String fileName, String key, long value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Activity.MODE_PRIVATE);
        sharedPreferences.edit().putLong(key, value).apply();
    }

    public static long getLong(Context context, String fileName, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Activity.MODE_PRIVATE);
        return sharedPreferences.getLong(key, -1L);
    }

    public static Boolean getBoolean(Context context, String strKey,Boolean strDefault) {//strDefault	boolean: Value to return if this preference does not exist.
        SharedPreferences setPreferences = context.getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        Boolean result = setPreferences.getBoolean(strKey, strDefault);
        return result;
    }

    public static void putBoolean(Context context, String strKey, Boolean strData) {
        SharedPreferences activityPreferences = context.getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putBoolean(strKey, strData);
        editor.commit();
    }
}
