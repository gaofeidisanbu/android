package com.yangcong345.foundation.jsbridge;

import org.json.JSONObject;

/**
 * Created by gaofei on 2018/2/24.
 */

public interface JsBridgeResponseCallback {
    void call(JSONObject data);
}
