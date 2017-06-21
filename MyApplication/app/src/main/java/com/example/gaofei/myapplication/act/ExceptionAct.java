package com.example.gaofei.myapplication.act;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.gaofei.myapplication.BaseAct;
import com.example.gaofei.myapplication.R;
import com.example.gaofei.myapplication.utils.MyClass;

/**
 * Created by gaofei on 2017/6/20.
 */

public class ExceptionAct extends BaseAct{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_exception);
        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    fun(null);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

    }

    public void fun(String s) {
        try {
            fun2(s);
        }catch (Exception e){
            e.printStackTrace();
            RuntimeException exception = new RuntimeException("dddddddddddd");
            exception.fillInStackTrace().initCause(e);
            throw  exception;
        }

    }

    public void fun2(String s){
       s.toString();
    }
}
