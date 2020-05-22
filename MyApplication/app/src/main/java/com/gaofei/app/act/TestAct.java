package com.gaofei.app.act;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.gaofei.app.R;
import com.gaofei.app.fra.BaseDialogFragment;
import com.gaofei.library.ProjectApplication;
import com.gaofei.library.base.BaseAct;
import com.gaofei.library.utils.FileUtils;
import com.gaofei.library.utils.LogUtils;
import com.yangcong345.webpage.BaseBridgeWebViewV2Activity;
import com.yangcong345.webpage.WebPageParam;

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

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test);
        TextView text = (TextView) findViewById(R.id.text);
        text.setText(stringFromJNI());
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebPageParam webPageParam = new WebPageParam("file:///android_asset/bridge/jsbridge.html",
                        "无法修改学校", true, true, true, true, 1, null);

                BaseBridgeWebViewV2Activity.Companion.navigateTo(TestAct.this, webPageParam);
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

}


