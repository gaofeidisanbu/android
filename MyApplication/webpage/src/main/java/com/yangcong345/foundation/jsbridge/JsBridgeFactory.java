package com.yangcong345.foundation.jsbridge;

import android.content.Context;
import androidx.annotation.NonNull;

/**
 * @author xianchaohua
 */

public class JsBridgeFactory {

    private JsBridgeFactory() {

    }

    public static JsBridge createJSBridge(Context context, @NonNull WebViewProxy proxy) {
        JsBridge jsBridge = new SimpleJsBridge();
        jsBridge.initBridge(context, proxy);
        return jsBridge;
    }

}
