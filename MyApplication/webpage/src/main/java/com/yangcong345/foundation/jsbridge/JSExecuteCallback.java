package com.yangcong345.foundation.jsbridge;

/**
 * Created by gaofei on 2018/2/24.
 */

public interface JSExecuteCallback<T> {
    void onReceiveValue(T value);
}
