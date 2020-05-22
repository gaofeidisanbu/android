package com.yangcong345.webpage.bridge.inter;

import com.yangcong345.webpage.YCResponseCallback;

import java.util.Map;


/**
 * Created by gaofei on 14/03/2018.
 */

public interface IPayCallback {
    void pay( YCResponseCallback<Map<String, Object>> responseCallBack);
}
