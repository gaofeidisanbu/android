package com.example.gaofei.myapplication;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by gaofei on 2017/5/26.
 */

public class MainApplication extends Application{
    private static Context context;
    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Log.d("MainApplication","------->onCreate");
    }

}
