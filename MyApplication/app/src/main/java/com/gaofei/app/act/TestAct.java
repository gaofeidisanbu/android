package com.gaofei.app.act;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.gaofei.app.R;
import com.gaofei.app.databinding.ActTestBinding;
import com.gaofei.app.http.OkhttpUtils;
import com.gaofei.app.plugin.EncryptUtil;
import com.gaofei.library.TestKotlin;
import com.gaofei.library.base.BaseAct;
import com.gaofei.library.utils.LogUtils;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by gaofei on 2017/6/29.
 */

public class TestAct extends BaseAct {
    private ActTestBinding mBinding;
    private boolean isToolBarShow = false;

    static {
//        LogUtils.d("TestAct init");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.act_test);
        TextView text = (TextView) findViewById(R.id.text);
        LogUtils.d(TAG + " main " + Thread.currentThread().getId());
        Observable.fromCallable(() -> {
            LogUtils.d(TAG + " fromCallable1 " + Thread.currentThread().getId());
            return new Object();
        }).subscribeOn(Schedulers.io())
                .map(o -> {
                    LogUtils.d(TAG + " map1 " + Thread.currentThread().getId());
                    return new Object();
                }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(o -> {
                    LogUtils.d(TAG + " map2 " + Thread.currentThread().getId());
                    return new Object();
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.d(TAG + " onSubscribe " + Thread.currentThread().getId());
                    }

                    @Override
                    public void onNext(Object o) {
                        LogUtils.d(TAG + " onNext " + Thread.currentThread().getId());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        TestKotlin testKotlin = new TestKotlin();
        testKotlin.main(new String[]{});

        mBinding.image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(TestAct.this, TaskActivity.class);
//                startActivity(intent);
                OkhttpUtils.INSTANCE.getDatasync();
            }
        });

        mBinding.image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestAct.this, TaskActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        mBinding.image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestAct.this, TaskActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        mBinding.image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestAct.this, TaskActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }




}
