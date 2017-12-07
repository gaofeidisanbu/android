package com.gaofei.library.utils;



import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by gaofei on 2017/6/19.
 */

public class WebPageUtils {
    private static String KEY_APP_VERSION = "appVersion";
    private static String KEY_CHANNEL = "channel";

    public static String addUrlPubParam(String url) {
        String url1 = addUrlParam(url, KEY_APP_VERSION, "1.2");
        String endUrl = addUrlParam(url1, KEY_CHANNEL, "xiaomi");
        return endUrl;
    }

    private static String addUrlParam(String url, String key, String value) {
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        if (!isUrlContainsParam(url, key)) {
            if (url.contains("?")) {
                sb.append("&");
            } else {
                sb.append("?");
            }
            sb.append(getUrlParam(key, value));
        }
        return sb.toString();
    }


    public static String getUrlParam(String param, String value) {
        return String.format("%s=%s", param, value);
    }

    public static boolean isUrlContainsParam(String url, String key) {
        if (url.contains("?")) {
            try {
                URL url1 = new URL(url);
                String str = url1.getQuery();
                if (!TextUtils.isEmpty(str)) {
                    String[] pairs = str.split("&");
                    for (String pair : pairs) {
                        String[] keyValue = pair.split("=");
                        if (TextUtils.equals(keyValue[0], key)) {
                            return true;
                        }
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
