package com.yangcong345.webpage;

/**
 * Created by drawf on 2018/3/27.
 * ------------------------------
 */
public class YCResponseCallbackWrapper<T, R> {
    private YCResponseCallback<R> ycResponseCallback;
    private YCResponseCallback2<R> ycResponseCallback2;

    public YCResponseCallbackWrapper() {
    }

    public YCResponseCallbackWrapper(YCResponseCallback<R> ycResponseCallback) {
        this.ycResponseCallback = ycResponseCallback;
    }

    public YCResponseCallbackWrapper(YCResponseCallback2<R> ycResponseCallback2) {
        this.ycResponseCallback2 = ycResponseCallback2;
    }

    public Response getResponse() {
        if (ycResponseCallback != null) {
            return ycResponseCallback;
        }
        if (ycResponseCallback2 != null) {
            return ycResponseCallback2;
        }
        return null;
    }

}
