
package com.gaofei.library.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;


import com.gaofei.library.ProjectApplication;

import java.lang.reflect.Field;

public class DimenUtils {

    private static final int DP_TO_PX = TypedValue.COMPLEX_UNIT_DIP;
    private static final int SP_TO_PX = TypedValue.COMPLEX_UNIT_SP;
    private static final int PX_TO_DP = TypedValue.COMPLEX_UNIT_MM + 1;
    private static final int PX_TO_SP = TypedValue.COMPLEX_UNIT_MM + 2;
    private static final int DP_TO_PX_SCALE_H = TypedValue.COMPLEX_UNIT_MM + 3;
    private static final int DP_SCALE_H = TypedValue.COMPLEX_UNIT_MM + 4;
    private static final int DP_TO_PX_SCALE_W = TypedValue.COMPLEX_UNIT_MM + 5;

    // -- dimens convert

    private static float applyDimension(Context context, int unit, float value, DisplayMetrics metrics) {
        switch (unit) {
            case DP_TO_PX:
            case SP_TO_PX:
                return TypedValue.applyDimension(unit, value, metrics);
            case PX_TO_DP:
                return value / metrics.density;
            case PX_TO_SP:
                return value / metrics.scaledDensity;
            case DP_TO_PX_SCALE_H:
                return TypedValue.applyDimension(DP_TO_PX, value * getScaleFactorH(context), metrics);
            case DP_SCALE_H:
                return value * getScaleFactorH(context);
            case DP_TO_PX_SCALE_W:
                return TypedValue.applyDimension(DP_TO_PX, value * getScaleFactorW(context), metrics);
        }
        return 0;
    }

    public static int dp2px(Context context, float value) {
        return (int) applyDimension(context, DP_TO_PX, value, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float value) {
        return (int) applyDimension(context, SP_TO_PX, value, context.getResources().getDisplayMetrics());
    }

    public static int sp2px( float value) {
        return (int) applyDimension(ProjectApplication.getContext(), SP_TO_PX, value,
                ProjectApplication.getContext().getResources().getDisplayMetrics());
    }

    public static int px2dp(Context context, float value) {
        return (int) applyDimension(context, PX_TO_DP, value, context.getResources().getDisplayMetrics());
    }

    public static int px2dp(float value) {
        return  px2dp(ProjectApplication.getContext(),value);
    }

    public static int px2sp(Context context, float value) {
        return (int) applyDimension(context, PX_TO_SP, value, context.getResources().getDisplayMetrics());
    }

    public static int px2sp(float value) {
        return px2sp(ProjectApplication.getContext(),value);
    }

    public static int dp2pxScaleW(Context context, float value) {
        return (int) applyDimension(context, DP_TO_PX_SCALE_W, value, context.getResources().getDisplayMetrics());
    }

    public static int dp2pxScaleH(Context context, float value) {
        return (int) applyDimension(context, DP_TO_PX_SCALE_H, value, context.getResources().getDisplayMetrics());
    }

    public static int dpScaleH(Context context, float value) {
        return (int) applyDimension(context, DP_SCALE_H, value, context.getResources().getDisplayMetrics());
    }

    private final static float BASE_SCREEN_WIDH = 720f;
    private final static float BASE_SCREEN_HEIGHT = 1280f;
    private final static float BASE_SCREEN_DENSITY = 2f;
    private static Float sScaleW, sScaleH;

    private static float getScaleFactorW(Context context) {
        if (sScaleW == null) {
            sScaleW = (getScreenWidth(context) * BASE_SCREEN_DENSITY) / (getDensity(context) * BASE_SCREEN_WIDH);
        }
        return sScaleW;
    }

    private static float getScaleFactorH(Context context) {
        if (sScaleH == null) {
            sScaleH = (getScreenHeight(context) * BASE_SCREEN_DENSITY)
                    / (getDensity(context) * BASE_SCREEN_HEIGHT);
        }
        return sScaleH;
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }




    public static int getWindowWidthForSMG_9500(Context activity){
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getContentHeightForSMG_9500(Context activity){
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    private static DisplayMetrics mMetrics = ProjectApplication.getContext().getResources()
            .getDisplayMetrics();
    private static WindowManager wm = (WindowManager) ProjectApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
    private static int windowWidth = -1;
    private static int windowHeight = -1;

    public static int dp2px(float value) {
        return (int) (0.5f + applyDimension(ProjectApplication.getContext(), DP_TO_PX, value, mMetrics));
    }

    public static int getWindowWidth() {
        try {
            if (windowWidth < 0) {
                Display display = wm.getDefaultDisplay();
                Point pp = new Point();
                display.getSize(pp);
                windowWidth = pp.x;
            }
            return windowWidth;
        } catch (Exception e) {

        }
        return mMetrics.widthPixels;
    }

    public static int getWindowHeight() {
        try {
            if (windowHeight < 0) {
                Display display = wm.getDefaultDisplay();
                Point pp = new Point();
                display.getSize(pp);
                windowHeight = pp.y;
            }
            return windowHeight;
        } catch (Exception e) {

        }
        return mMetrics.heightPixels;
    }

    public static int getContentHeight2() {
        int height = mMetrics.heightPixels - getStatusBarHeight2();
        return height;
    }

    public static int getStatusBarHeight2() {

        if (isXiaoMiNavigationGestureEnabled(ProjectApplication.getContext())) {
            return 0;
        }
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = ProjectApplication.getContext().getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    //判断小米手机有没有开启全面屏手势
    public static boolean isXiaoMiNavigationGestureEnabled(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "force_fsg_nav_bar", 0) != 0 ;
    }
    public static void updateLayout(View view, int w, int h) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null)
            return;
        if (w != -3)
            params.width = w;
        if (h != -3)
            params.height = h;
        view.setLayoutParams(params);
    }
    public static int getContentHeight(Activity activity) {
        int height = mMetrics.heightPixels - getStatusBarHeight(activity);
        return height;
    }
    public static int getStatusBarHeight(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

    private static int mViewHeight;
    private static int mViewWidth;
    public static void setViewHeight(int viewHeight) {
        mViewHeight = Math.abs(viewHeight);
    }

    public static void setViewWidth(int viewWidth) {
        mViewWidth = Math.abs(viewWidth);
    }

    public static int getmViewHeight() {
        if (mViewHeight == 0) {
            return getWindowHeight();
        }
        return mViewHeight;
    }

    public static int getmViewWidth() {
        if (mViewWidth == 0) {
            return getWindowWidth();
        }
        return mViewWidth;
    }

    // 竖屏
    public static boolean isVertical() {
        return ProjectApplication.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    //折疊屏切換屏幕時，需要清空緩存的屏幕寬高。
    public static void clearWindowWAndH() {
        windowHeight = -1;
        windowWidth = -1;
        sScaleW = null;
        sScaleH = null;
    }

    //是否是華為折疊屏
    public static boolean isHUAWEI_X() {
        return Build.MODEL.contains("TAH-N29");
    }

    public static boolean isSamsungFold() {
        return Build.MODEL.contains("SM-F9000");
    }
}
