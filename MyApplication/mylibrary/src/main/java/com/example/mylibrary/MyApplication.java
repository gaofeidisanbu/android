package com.example.mylibrary;

import android.app.Application;
import android.util.Log;

/**
 * Created by gaofei on 2017/5/25.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyApplication","-----> onCreate");
    }
}
