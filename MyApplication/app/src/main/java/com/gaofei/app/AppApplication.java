package com.gaofei.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.webkit.WebView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.gaofei.app.act.webview.WebViewPool;
import com.gaofei.library.base.ProjectProxy;

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
//        WebViewPool.INSTANCE.init();

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
