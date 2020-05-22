package com.yangcong345.webpage.bridge.inter;

import com.yangcong345.webpage.YCResponseCallback;

import java.util.Map;

/**
 * @author xianchaohua
 */

public interface IWebView {

    void showShowShareIcon(Map<String, String> params,  YCResponseCallback callback);

    void showCustomerService(Map<String, String> params, YCResponseCallback callback);

    void callPhone(Map<String, String> params, YCResponseCallback callback);

}
