package com.yangcong345.foundation.jsbridge;

import android.webkit.JavascriptInterface;

/**
 * @author xianchaohua
 */

public class WebAppInterface {

    private SimpleJsBridge mSimpleJsBridge;

    public WebAppInterface(SimpleJsBridge simpleJsBridge) {
        mSimpleJsBridge = simpleJsBridge;
    }

    /**
     * @param protocol:{"methodName":"", "dispatchId":"", "params":{}}
     */
    @JavascriptInterface
    public void evaluateJava(String protocol) {
        this.mSimpleJsBridge.evaluateJava(protocol);
    }

    @JavascriptInterface
    public void responseCallback(String protocol) {
        this.mSimpleJsBridge.evaluateResponseCallback(protocol);
    }

}
