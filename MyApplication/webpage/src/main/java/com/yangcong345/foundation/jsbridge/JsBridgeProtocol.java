package com.yangcong345.foundation.jsbridge;

import org.json.JSONObject;

/**
 * Created by gaofei on 28/02/2018.
 */

public interface JsBridgeProtocol {
    JSONObject jsCallNativeRequest(String protocol);

    String jsCallNativeRequestToRes(JSONObject input, JSONObject output);

    String nativeCallJsRequest(JSONObject input);

    String nativeCallJsRequestToRes(String protocol);
}
