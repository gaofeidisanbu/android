package com.yangcong345.foundation.jsbridge;



/**
 * @author xianchaohua
 */

public interface WebViewProxy {

    void loadUrl(String url);

    void addJavascriptInterface(Object object, String name);

    void removeJavascriptInterface(String name);

    void evaluateJavascript(String script, JSExecuteCallback<String> resultCallback);

    boolean isPageFinished();

    void injectScript(String script);
}
