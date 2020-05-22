package com.yangcong345.foundation.jsbridge;

import android.text.TextUtils;

/**
 * Created by gaofei on 27/02/2018.
 */

public class JsBridgeConstant {
    /**
     * native 注册方法相关key
     */
    public static final String KEY_REGISTER_CALL_METHOD_NAME = "eventName";
    public static final String KEY_REGISTER_CALL_ID = "dispatchId";
    public static final String KEY_REGISTER_CALL_PARAMS = "params";

    public static final String KEY_REGISTER_RESPONSE_METHOD_NAME = "eventName";
    public static final String KEY_REGISTER_RESPONSE_ID = "dispatchId";
    public static final String KEY_REGISTER_RESPONSE_PARAMS = "params";
    public static final String REGISTER_ID_VALUE_NOT_RESPONSE = "";
    /**
     * js 注册方法相关key
     */
    public static final String KEY_DISPATCH_CALL_METHOD_NAME = "eventName";
    public static final String KEY_DISPATCH_CALL_ID = "dispatchId";
    public static final String KEY_DISPATCH_CALL_PARAMS = "params";

    public static final String KEY_DISPATCH_RESPONSE_METHOD_NAME = "eventName";
    public static final String KEY_DISPATCH_RESPONSE_ID = "dispatchId";
    public static final String KEY_DISPATCH_RESPONSE_PARAMS = "params";
    public static final String DISPATCH_RESPONSE_VALUE_NOT_RESPONSE = "NOOP";

    /**
     * log
     */
    public static final String LOG_JS_CALL_NATIVE_REQUEST_MESSAGE = "js call native for request message";
    public static final String LOG_JS_CALL_NATIVE_RESPONSE_MESSAGE = "js call native for response message";
    public static final String LOG_NATIVE_CALL_JS_REQUEST_MESSAGE = "native call js for request message";
    public static final String LOG_NATIVE_CALL_JS_RESPONSE_MESSAGE = "native call js for response message";

    /**
     * js调用native方法时，是否需要回调
     *
     * @param id
     * @param input
     * @return
     */
    public static boolean isResponseToJs(String id, String input) {
        return !TextUtils.equals(id, REGISTER_ID_VALUE_NOT_RESPONSE);
    }
}
