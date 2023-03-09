package com.gaofei.app.act;

import android.os.Bundle;
import androidx.annotation.Nullable;

import android.view.View;

import com.gaofei.app.R;
import com.gaofei.library.base.BaseAct;
import com.gaofei.library.utils.LogUtils;

/**
 * Created by gaofei on 2017/6/20.
 */

public class ExceptionAct extends BaseAct {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_exception);
        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try {
//                    ExceptionTest exception = new ExceptionTest();
//                    exception.fun(null);
//                } catch (Exception e) {
//                    Log.e("ycmaaa", "cdaaaaaaaaa", e);
//                }
//
//                try {
//                    ExceptionTest exception = new ExceptionTest();
//                    exception.fun3(null);
//                } catch (Exception e) {
//                    Log.e("ycmaaa", "cdaaaaaaaaa", e);
//                }
//
                ExceptionTest exception = new ExceptionTest();
                exception.fun4(null);
//                final Thread mainThread = Thread.currentThread();
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
////                        throw new RuntimeException("Thread");
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        Log.i("aaa", mainThread.getState().toString());
//                    }
//                }).start();
                LogUtils.d("aaa "+Thread.currentThread().getId());
                throw new RuntimeException("test");

            }
        });

    }


}
