package com.yangcong345.foundation.jsbridge;

import android.content.Context;
import androidx.annotation.NonNull;

import org.json.JSONObject;

/**
 * @author xianchaohua
 */

public interface JsBridgeMethod {

    void call(Context context, @NonNull JSONObject params, @NonNull JsBridgeCallback callback);
}
