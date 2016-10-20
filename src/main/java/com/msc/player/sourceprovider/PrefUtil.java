package com.msc.player.sourceprovider;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by bohdantrofymchuk on 4/6/14.
 */
public class PrefUtil {

    public static void saveString(Context context, String prefCategory, String key, String value) {
        SharedPreferences.Editor editor = getEditor(context, prefCategory);
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(Context context, String prefCategory, String key) {
        return getSharedPreferences(context, prefCategory).getString(key, "");
    }

    public static void saveInt(Context context, String prefCategory, String key, int value) {
        SharedPreferences.Editor editor = getEditor(context, prefCategory);
        editor.putInt(key, value);
        editor.commit();
    }

    public static void saveLong(Context context, String prefCategory, String key, long value) {
        SharedPreferences.Editor editor = getEditor(context, prefCategory);
        editor.putLong(key, value);
        editor.commit();
    }

    public static int getInt(Context context, String prefCategory, String key, int defValue) {
        return getSharedPreferences(context, prefCategory).getInt(key, defValue);
    }

    public static long getLong(Context context, String prefCategory, String key, long defValue) {
        return getSharedPreferences(context, prefCategory).getLong(key, defValue);
    }

    public static void saveBoolean(Context context, String prefCategory, String key, boolean value) {
        SharedPreferences.Editor editor = getEditor(context, prefCategory);
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolean(Context context, String prefCategory, String key, boolean defValue) {
        return getSharedPreferences(context, prefCategory).getBoolean(key, defValue);
    }

    private static SharedPreferences.Editor getEditor(Context context, String prefCategory) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefCategory, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        return edit;
    }

    private static SharedPreferences getSharedPreferences(Context context, String prefCategory) {
        SharedPreferences pref = context.getSharedPreferences(prefCategory, Context.MODE_MULTI_PROCESS);
        return pref;
    }

    public static SharedPreferences getDefaultSharedPreferencesInMustiProcess(Context context) {
        return context.getSharedPreferences(context.getPackageName() + "_preferences", Context.MODE_MULTI_PROCESS);
    }

}
