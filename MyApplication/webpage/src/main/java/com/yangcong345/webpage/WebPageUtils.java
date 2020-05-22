package com.yangcong345.webpage;

import android.text.TextUtils;


import com.yangcong345.webpage.log.LogUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaofei on 2017/6/19.
 */

public class WebPageUtils {
    private static final String KEY_APP_VERSION = "appVersion";
    private static final String KEY_CHANNEL = "channel";
    private static final String KEY_PLATFORM = "platform";
    private static final String KEY_ENV = "env";
    private static final String KEY_APP_CATEGORY = "appCategory";
    private static final String KEY_DEVICE = "device";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_CLIENT_TYPE = "clientType";
    private static final String KEY_CLIENT_CHANNEL = "clientChannel";
    private static final String CLIENT_TYPE_ANDROID = "android-app";
    private static final String KEY_EXERCISE_DEBUG = "exerciseDebug";
    private static final String KEY_ENVIRONMENT = "environment";
    private static boolean isReleaseEnvironment;

    public static void initEnv(boolean isReleaseEnvironment) {
        WebPageUtils.isReleaseEnvironment = isReleaseEnvironment;
    }

    public static String addUrlPubParam(String url) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        String newUrl = url;
        if (url.startsWith("http:") || url.startsWith("https:") || url.startsWith("file:")) {
            Map<String, Object> map = new HashMap<>();
        }
        LogUtils.d("WebPageUtils newUrl = " + newUrl);
        return newUrl;
    }
}
