package com.yangcong345.webpage;

import com.yangcong345.webpage.bridge.inter.Request;

/**
 * Created by gaofei on 06/02/2018.
 */

public interface YCAction2<R> extends Request {
    void request(YCResponseCallback<R> responseCallBack);
}
