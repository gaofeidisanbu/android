package com.yangcong345.webpage.bridge;

import com.yangcong345.foundation.jsbridge.JsBridgeConstant;
import com.yangcong345.foundation.jsbridge.JsBridgeJsCallInterceptor;
import com.yangcong345.webpage.log.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gaofei on 27/02/2018.
 */

public class OnionJsCallInterceptor implements JsBridgeJsCallInterceptor {

    @Override
    public String input(String methodId, String methodName, JSONObject params) throws JSONException {
        JSONObject protocol = new JSONObject();
        protocol.put(JsBridgeConstant.KEY_DISPATCH_CALL_ID, methodId);
        protocol.put(JsBridgeConstant.KEY_DISPATCH_CALL_METHOD_NAME, methodName);
        protocol.put(JsBridgeConstant.KEY_DISPATCH_CALL_PARAMS, params);
        LogUtils.d(String.format("%s -> protocol = %s", JsBridgeConstant.LOG_NATIVE_CALL_JS_REQUEST_MESSAGE, protocol.toString()));
        return Base64Util.encode(protocol.toString());
    }

    @Override
    public JSONObject output(String output) throws JSONException {
        String value = Base64Util.decode(output);
        JSONObject outputData = new JSONObject(BridgeUtils.backslashReplace(value));
        LogUtils.d(String.format("%s -> protocol = %s", JsBridgeConstant.LOG_NATIVE_CALL_JS_RESPONSE_MESSAGE, outputData.toString()));
        return outputData;
    }
}
