package com.yangcong345.webpage.bridge.inter;

import com.yangcong345.webpage.YCAction;
import com.yangcong345.webpage.YCAction1;
import com.yangcong345.webpage.YCAction2;
import com.yangcong345.webpage.YCHandler;

/**
 * Created by gaofei on 11/02/2018.
 */

public final class ServiceRequestWrapper<T, R> {

    private String mMethodName;

    private Request mRequest;

    public ServiceRequestWrapper(String methodName, YCAction request) {
        this.mMethodName = methodName;
        this.mRequest = request;
    }

    public ServiceRequestWrapper(String methodName, YCAction1<T> request1) {
        this.mMethodName = methodName;
        this.mRequest = request1;
    }

    public ServiceRequestWrapper(String methodName, YCAction2<R> request2) {
        this.mMethodName = methodName;
        this.mRequest = request2;
    }

    public ServiceRequestWrapper(String methodName, YCHandler<T, R> request3) {
        this.mMethodName = methodName;
        this.mRequest = request3;
    }

    public String getMethodName() {
        return mMethodName;
    }

    public Request getRequest() {
        return mRequest;
    }
}
