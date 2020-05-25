package com.yangcong345.webpage.support;

import android.webkit.JavascriptInterface;

import com.yangcong345.foundation.jsbridge.JSExecuteCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author gaofei
 * @Date 2019/1/30 2:25 PM
 */
public class JavascriptInterfaceCompatible {
    Map<String, JSExecuteCallback> map = new HashMap<>();

    public void addCallback(String key, JSExecuteCallback callback) {
        map.put(key, callback);
    }

    @JavascriptInterface
    public void onResultForScript(String key, String value) {
        JSExecuteCallback callback = map.remove(key);
        if (callback != null)
            callback.onReceiveValue(value);
    }
}
