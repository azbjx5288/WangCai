package com.wangcai.lottery.base;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 本地数据缓存.
 */
public class Preferences {

    private static final String SANDBOX = "shared_prefs";

    public static int getInt(Context context, String key, int defValue) {
        SharedPreferences pref = context.getSharedPreferences(SANDBOX, Context.MODE_PRIVATE);
        return pref.getInt(key, defValue);
    }

    public static void saveInt(Context context, String key, int value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SANDBOX, Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static long getLong(Context context, String key, long defValue) {
        SharedPreferences pref = context.getSharedPreferences(SANDBOX, Context.MODE_PRIVATE);
        return pref.getLong(key, defValue);
    }

    public static void saveLong(Context context, String key, long value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SANDBOX, Context.MODE_PRIVATE).edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static float getFloat(Context context, String key, float defValue) {
        SharedPreferences pref = context.getSharedPreferences(SANDBOX, Context.MODE_PRIVATE);
        return pref.getFloat(key, defValue);
    }

    public static void saveFloat(Context context, String key, float value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SANDBOX, Context.MODE_PRIVATE).edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static String getString(Context context, String key, String defValue) {
        SharedPreferences pref = context.getSharedPreferences(SANDBOX, Context.MODE_PRIVATE);
        return pref.getString(key, defValue);
    }

    public static void saveString(Context context, String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SANDBOX, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static Boolean getBoolean(Context context, String key, boolean defValue) {
        SharedPreferences pref = context.getSharedPreferences(SANDBOX, Context.MODE_PRIVATE);
        return pref.getBoolean(key, defValue);
    }

    public static void saveBoolean(Context context, String key, Boolean status) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SANDBOX, Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, status);
        editor.apply();
    }

    public static void delete(Context context, String key) {
        context.getSharedPreferences(SANDBOX, Context.MODE_PRIVATE).edit().remove(key).commit();
    }
}
