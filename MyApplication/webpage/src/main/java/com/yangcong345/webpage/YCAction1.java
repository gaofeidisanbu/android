package com.yangcong345.webpage;

import com.yangcong345.webpage.bridge.inter.Request;

/**
 * Created by gaofei on 06/02/2018.
 */

public interface YCAction1<T> extends Request {
    void request(T data);
}
