package com.gaofei.app.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.gaofei.app.R;
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
    private boolean isToolBarShow = false;

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.act_test);
        TextView text = (TextView) findViewById(R.id.text);
        text.setText(stringFromJNI());
    }


    public native String stringFromJNI();

}
