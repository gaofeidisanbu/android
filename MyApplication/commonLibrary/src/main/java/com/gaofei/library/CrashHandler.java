package com.gaofei.library;

import android.os.Process;

import com.gaofei.library.utils.LogUtils;

/**
 * Created by gaofei on 2017/9/16.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private Thread.UncaughtExceptionHandler mDefaultUncaughtExceptionHandler;
    public CrashHandler(Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler){
        this.mDefaultUncaughtExceptionHandler = defaultUncaughtExceptionHandler;
    }
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        LogUtils.d(e.getMessage());
        mDefaultUncaughtExceptionHandler.uncaughtException(t,e);
    }
}
