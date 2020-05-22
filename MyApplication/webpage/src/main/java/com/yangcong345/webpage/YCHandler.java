package com.yangcong345.webpage;

import com.yangcong345.webpage.bridge.inter.Request;

/**
 * Created by gaofei on 06/02/2018.
 */

public interface YCHandler<T, R> extends Request {
    void request(T requestData, YCResponseCallback<R> responseCallBack);
}
