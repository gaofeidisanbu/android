package com.gaofei.app.act;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.gaofei.app.R;
import com.gaofei.app.anr.CPU;
import com.gaofei.app.fra.BaseDialogFragment;
import com.gaofei.library.ProjectApplication;
import com.gaofei.library.base.BaseAct;
import com.gaofei.library.utils.FileUtils;
import com.gaofei.library.utils.LogUtils;
import com.yangcong345.webpage.BaseBridgeWebViewV2Activity;
import com.yangcong345.webpage.WebPageParam;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by gaofei on 2017/6/29.
 */

public class TestAct extends BaseAct {
    private boolean isToolBarShow = false;
    private static List<CPU> list = new ArrayList<>();
    private static List<Activity> list1 = new ArrayList<>();
    byte[] bytes1;


    static {
        System.loadLibrary("native-lib");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File dir = getExternalFilesDir(null);
        list1.add(this);
        LogUtils.d(dir.toString());
//        Debug.startMethodTracing("shixintrace");
        list.add(new CPU());
        EventBus.getDefault().register(this);
        setContentView(R.layout.act_test);
        TextView text = (TextView) findViewById(R.id.text);
        text.setText(stringFromJNI());
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                WebPageParam webPageParam = new WebPageParam("file:///android_asset/bridge/jsbridge.html",
//                        "无法修改学校", true, true, true, true, 1, null);
//
//                BaseBridgeWebViewV2Activity.Companion.navigateTo(TestAct.this, webPageParam);
//                EventBus.getDefault().post(new MessageEvent());
////                try {
////                    byte[] bytes = new byte[100*10*1024*1024];
////                    bytes.toString();
////                }catch (OutOfMemoryError e)  {
////                    LogUtils.e(e);
////                    bytes1 = new byte[100*1024*1024];
////                    bytes1.toString();
////                }
                ContentResolver cr = getContentResolver();
                Uri uri = Uri.parse("content://com.test.demo.fileprovider/test");
                cr.query(uri,null,null,null,null);
            }
        });

    }


    private void testAnr() {
        int i = 0;

        for (i = 0; i < 1000; i++) {
            new Thread(new RunnableTest()).start();
        }
        for (i = 0; i < 20000; i++) {
            for (int j = 0; j < 20000; j++) {
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        LogUtils.d("");
    }

    public native String stringFromJNI();

    static class RunnableTest implements Runnable {
        @Override
        public void run() {
            int i = 0;
            int j = 0;
            for (i = 0; i < 10000000; i++) {
                new Object();
                new Object();
                new Object();
                new Object();
                new Object();
                for (j = 0; j < 10000000; j++) {
                    new Object();
                    new Object();
                    new Object();
                    new Object();
                    new Object();
                    FileUtils.save(ProjectApplication.getContext(), "aaaaaaaaaaaaaaaaaaaaaaaaaaa");
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Debug.stopMethodTracing();
    }
}


