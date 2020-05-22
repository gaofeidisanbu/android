package com.yangcong345.foundation.jsbridge;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author xianchaohua
 */

public interface JsBridgeJsCallInterceptor {

    /**
     * 从前端传过来的数据
     * @param params
     * @return
     */
    String input(String methodId, String methodName, JSONObject params) throws JSONException;

    /**
     * 调用JsBridgeCallback.callback返回给前端的数据，用于添加额外的封装
     * @param output
     * @return
     */
    JSONObject output(String output) throws JSONException;

}
