package com.gaofei.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.gaofei.library.base.ProjectProxy;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by gaofei on 2017/5/26.
 */

public class AppApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        ProjectProxy.getInstance().setApplication(this);
        ProjectProxy.getInstance().onCreate();
        Log.d("ProjectApplication", "------->onCreate");

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ProjectProxy.getInstance().onTerminate();
    }
}
