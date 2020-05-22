package com.yangcong345.foundation.jsbridge;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author xianchaohua
 */

public interface JsBridgeInterceptor {

    /**
     * 从前端传过来的数据
     * @param input
     * @return
     */
    String input(String input);

    /**
     * 调用JsBridgeCallback.callback返回给前端的数据，用于添加额外的封装
     * @param input
     * @param output
     * @return
     */
    String output(String input, JSONObject output) throws JSONException;

}
