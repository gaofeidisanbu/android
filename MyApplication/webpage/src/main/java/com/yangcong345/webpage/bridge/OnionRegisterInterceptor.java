package com.yangcong345.webpage.bridge;

import com.yangcong345.foundation.jsbridge.JsBridgeConstant;
import com.yangcong345.foundation.jsbridge.JsBridgeInterceptor;
import com.yangcong345.webpage.log.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author xianchaohua
 */

public class OnionRegisterInterceptor implements JsBridgeInterceptor {

    public static final String TAG = "OnionRegisterInterceptor";

    @Override
    public String input(String input) {
        input = BridgeUtils.backslashReplace(Base64Util.decode(input));
        LogUtils.d(String.format("%s -> protocol = %s", JsBridgeConstant.LOG_JS_CALL_NATIVE_REQUEST_MESSAGE, input));
        return input;
    }

    @Override
    public String output(String input, JSONObject output) throws JSONException {
        JSONObject inputData = new JSONObject(input);
        JSONObject responseData = new JSONObject();
        responseData.put(JsBridgeConstant.KEY_REGISTER_RESPONSE_METHOD_NAME, inputData.opt(JsBridgeConstant.KEY_REGISTER_CALL_ID));
        responseData.put(JsBridgeConstant.KEY_REGISTER_RESPONSE_PARAMS, output);
        LogUtils.d(String.format("%s -> protocol = %s", JsBridgeConstant.LOG_JS_CALL_NATIVE_RESPONSE_MESSAGE, responseData.toString()));
        return Base64Util.encode(responseData.toString());
    }

}
