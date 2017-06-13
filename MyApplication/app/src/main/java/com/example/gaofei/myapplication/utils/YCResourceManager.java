package com.example.gaofei.myapplication.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.RawRes;
import android.support.annotation.StringRes;

import com.example.gaofei.myapplication.MainApplication;

/**
 * Created by liushuo on 16/5/11.
 */
public class YCResourceManager {

    public static AssetManager getAssets() {
        Context context = MainApplication.getContext();
        if (context == null) throw new NullPointerException("can't get Application Context");

        return context.getAssets();
    }
    public static Resources getResource() {
        Context context = MainApplication.getContext();
        if (context == null) throw new NullPointerException("can't get Application Context");

        return context.getResources();
    }

    public static Uri getRawResource(@RawRes int id) {
        Context context = MainApplication.getContext();
        if (context == null) throw new NullPointerException("can't get Application Context");

        return Uri.parse("android.resource://" + context.getPackageName() + "/" + id);

    }

    public static String getString(@StringRes int resId) {
        Resources res = getResource();

        return res.getString(resId);
    }

    public static String getString(@StringRes int resId, Object... formatArgs) {
        Resources res = getResource();

        return res.getString(resId, formatArgs);
    }

    public static String[] getStringArray(@ArrayRes int resId) {
        Resources res = getResource();

        return res.getStringArray(resId);
    }

    public static float getDimen(@DimenRes int resId) {
        Resources res = getResource();

        return res.getDimension(resId);
    }

    @ColorInt
    public static int getColor(@ColorRes int resId) {
        Resources res = getResource();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return res.getColor(resId, MainApplication.getContext().getTheme());
        }

        return res.getColor(resId);
    }

    public static Drawable getDrawable(@DrawableRes int resId) {
        Resources res = getResource();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return res.getDrawable(resId, MainApplication.getContext().getTheme());
        }

        return res.getDrawable(resId);

    }
}
