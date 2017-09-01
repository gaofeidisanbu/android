package com.gaofei.library.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.gaofei.library.ProjectApplication;


/**
 * Copyright (c) 2014 Guanghe.tv All right reserved.
 * --------------------<-.->-----------------------
 * Author:      Nono(陈凯)
 * CreateDate:  14/11/26
 * Description: exp...
 * Version:     1.0.0
 */
public class GFPreferenceManager {

    private static final String TAG = GFPreferenceManager.class.getSimpleName();

    /**
     * pre  keys
     */
    public static final String KEY_APP_CHANNEL              = "app_channel"; //guanghexinzhi
    public static final String KEY_UPDATE_TYPE_CONFIG       = "update_type_config"; //force_4
    public static final String KEY_IGNORE_VERSION           = "ignore_version";//2.0_
    public static final String KEY_VIDEO_HISTORY_TIME       = "video_hisory_time";//jsonArray[{1},[2}...]
    public static final String KEY_SPLASH_LOGO_URL          = "splash_logo_url";//jsonArray[{1},[2}...]
    public static final String KEY_TRACK_UPDATE_VERSION     = "post_track_update_version";
    public static final String KEY_VIDEO_DATA               = "video_data";
    public static final String KEY_PUSH_ABLE                = "push_able";
    public static final String KEY_NO_LOGIN_COOKIE          = "no_login_cookie";
    public static final String KEY_MAIN_CHAPTER_EXPAND      = "chapter_expand";
    public static final String KEY_COURSE_GUIDE_USER_CLOSED = "course_guide_user_closed";

    /**
     * 全局配置信息
     */

    /*是否忽略缓存提示*/
    public static final String KEY_IGNORE_CACHE_CONFIRM = "ignore_cache_confirm";






    /**
     * 默认偏好文件名
     */
    private static final String DEFAULT_SHARED_PREFERENCES_NAME = "yc_shared_prefer";
    /**
     * 访问为私有模式
     */
    private static final int    DEFAULT_FILE_MODE               = Context.MODE_PRIVATE;

    /*********
     * for save preference value and clear
     ***********/

    public static boolean putInt(String key, int value) {
        Context context = ProjectApplication.getContext();
        if (context == null) return false;

        SharedPreferences sp = context.getSharedPreferences(
                DEFAULT_SHARED_PREFERENCES_NAME, DEFAULT_FILE_MODE);
        return sp.edit().putInt(key, value).commit();
    }


    public static boolean putLong(String key, long value) {
        Context context = ProjectApplication.getContext();
        if (context == null) return false;

        SharedPreferences sp = context.getSharedPreferences(
                DEFAULT_SHARED_PREFERENCES_NAME, DEFAULT_FILE_MODE);
        return sp.edit().putLong(key, value).commit();
    }


    public static boolean putString(String key, String value) {
        Context context = ProjectApplication.getContext();
        if (context == null) return false;

        SharedPreferences sp = context.getSharedPreferences(
                DEFAULT_SHARED_PREFERENCES_NAME, DEFAULT_FILE_MODE);
        return sp.edit().putString(key, value).commit();
    }

    public static boolean putBoolean(String key, boolean value) {
        Context context = ProjectApplication.getContext();
        if (context == null) return false;

        SharedPreferences sp = context.getSharedPreferences(
                DEFAULT_SHARED_PREFERENCES_NAME, DEFAULT_FILE_MODE);
        return sp.edit().putBoolean(key, value).commit();
    }


    public static void clearAll() {
        Context context = ProjectApplication.getContext();
        if (context == null) return;

        SharedPreferences sp = context.getSharedPreferences(
                DEFAULT_SHARED_PREFERENCES_NAME, DEFAULT_FILE_MODE);
        sp.edit().clear().commit();
    }


    /***********
     * for get preference value
     *************/

    public static int getIntValue(String key) {
        return getIntValue(key, 0);
    }


    public static int getIntValue(String key, int defaultValue) {
        Context context = ProjectApplication.getContext();
        if (context == null) return defaultValue;

        SharedPreferences sp = context.getSharedPreferences(
                DEFAULT_SHARED_PREFERENCES_NAME, DEFAULT_FILE_MODE);
        return sp.getInt(key, defaultValue);
    }


    public static float getFloatValue(String key) {
        return getFloatValue(key, 0);
    }

    public static float getFloatValue(String key, long defaultValue) {
        Context context = ProjectApplication.getContext();
        if (context == null) return defaultValue;

        SharedPreferences sp = context.getSharedPreferences(
                DEFAULT_SHARED_PREFERENCES_NAME, DEFAULT_FILE_MODE);
        return sp.getFloat(key, defaultValue);
    }


    public static boolean putFloat(String key, float value) {
        Context context = ProjectApplication.getContext();
        if (context == null) return false;
        SharedPreferences sp = context.getSharedPreferences(
                DEFAULT_SHARED_PREFERENCES_NAME, DEFAULT_FILE_MODE);
        return sp.edit().putFloat(key, value).commit();
    }


    public static long getLongValue(String key) {
        return getLongValue(key, 0);
    }


    public static long getLongValue(String key, long defaultValue) {
        Context context = ProjectApplication.getContext();
        if (context == null) return defaultValue;

        SharedPreferences sp = context.getSharedPreferences(
                DEFAULT_SHARED_PREFERENCES_NAME, DEFAULT_FILE_MODE);
        return sp.getLong(key, defaultValue);
    }


    public static boolean getBooleanValue(String key) {
        Context context = ProjectApplication.getContext();
        if (context == null) return false;

        SharedPreferences sp = context.getSharedPreferences(
                DEFAULT_SHARED_PREFERENCES_NAME, DEFAULT_FILE_MODE);
        return sp.getBoolean(key, false);
    }


    public static boolean getBooleanValue(String key, boolean defVale) {
        Context context = ProjectApplication.getContext();
        if (context == null) return defVale;

        SharedPreferences sp = context.getSharedPreferences(
                DEFAULT_SHARED_PREFERENCES_NAME, DEFAULT_FILE_MODE);
        return sp.getBoolean(key, defVale);
    }


    public static String getStringValue(String key) {
        return getStringValue(key, null);
    }

    public static String getStringValue(String key, String defaultValue) {
        Context context = ProjectApplication.getContext();
        if (context == null) return defaultValue;

        SharedPreferences sp = context.getSharedPreferences(
                DEFAULT_SHARED_PREFERENCES_NAME, DEFAULT_FILE_MODE);
        return sp.getString(key, defaultValue);
    }

}