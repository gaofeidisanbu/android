package com.gaofei.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.webkit.WebView;

//import com.example.myapp.MyEventBusIndex;
//import com.example.myapp.MyEventBusIndex;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.gaofei.app.act.webview.WebViewPool;
import com.gaofei.library.base.ProjectProxy;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by gaofei on 2017/5/26.
 */

public class AppApplication extends Application {
    private static Context context;

    private static WebView webView;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        ProjectProxy.getInstance().setApplication(this);
        ProjectProxy.getInstance().onCreate();
        Log.d("ProjectApplication", "------->onCreate");
        Fresco.initialize(this);
//        EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
// Now the default instance uses the given index. Use it like this:
        EventBus eventBus = EventBus.getDefault();

    }

    public static Context getAppContext() {
        return context;
    }



    @Override
    public void onTerminate() {
        super.onTerminate();
        ProjectProxy.getInstance().onTerminate();
    }
}
