package com.yangcong345.webpage;

/**
 * Created by gaofei on 06/02/2018.
 */

public interface YCResponseCallback<R> extends Response {

    void callback(boolean status, R responseData);

}
