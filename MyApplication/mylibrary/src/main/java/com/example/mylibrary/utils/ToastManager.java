package com.example.mylibrary.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;

import com.example.mylibrary.BuildConfig;
import com.example.mylibrary.ProjectApplication;

/**
 * Author: xumin
 * Date: 16/4/24
 */
public class ToastManager {
    private static Context sContext = ProjectApplication.getContext();
    private static Handler sHandler = new Handler(Looper.getMainLooper());
    private static Toast sToast;

    private static final long DELAY = 600;
    private static String sLastMessage;
    private static long sLastShow;

    public static void show(final String message) {
        show(message, Toast.LENGTH_SHORT);
    }

    public static void show(final String message, final int duration) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                String text = message;
                if (BuildConfig.DEBUG) {
                    if (System.currentTimeMillis() - sLastShow <= DELAY && sLastMessage != null) {
                        text = sLastMessage + "\n\n" + message;
                    }

                    sLastMessage = message;
                    sLastShow = System.currentTimeMillis();
                }

                if (sToast == null) {
                    sToast = Toast.makeText(sContext, text, duration);
                } else {
                    sToast.setText(text);
                    sToast.setDuration(duration);
                }

                sToast.setGravity(Gravity.CENTER, 0, 0);
                sToast.show();
            }
        });
    }

    public static void show(int resId) {
        show(sContext.getString(resId));
    }

    public static void show(int resId, int duration) {
        show(sContext.getString(resId), duration);
    }
}
