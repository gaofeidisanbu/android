package com.yangcong345.foundation.jsbridge;

import android.content.Context;

import org.json.JSONObject;

/**
 * @author xianchaohua
 */

public interface JsBridge {

    void initBridge(Context context, WebViewProxy proxy);

    void addJSBridgeMethod(String method, JsBridgeMethod jsBridgeMethod);

    void removeJSBridgeMethod(String method);

    void clearJSBridgeMethod();

    void setJsCallbackName(String callbackName);

    void setJsInterfaceName(String jsInterfaceName);

    void setJsBridgeRegisterInterceptor(JsBridgeInterceptor interceptor);

    void setJsBridgeJsCallInterceptor(JsBridgeJsCallInterceptor interceptor);

    void callJSBridgeMethod(String methodId, String methodName, JSONObject data, JsBridgeResponseCallback responseCallback);

    void pageFinished();

    void injectScript(String script);
}
