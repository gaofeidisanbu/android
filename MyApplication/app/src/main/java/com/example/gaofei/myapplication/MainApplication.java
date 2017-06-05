package com.example.gaofei.myapplication;

import android.app.Application;
import android.util.Log;

/**
 * Created by gaofei on 2017/5/26.
 */

public class MainApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MainApplication","------->onCreate");
    }
}
