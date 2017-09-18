package com.gaofei.app.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.gaofei.app.R;
import com.gaofei.library.base.BaseAct;

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
                ExceptionTest exception = new ExceptionTest();
                exception.fun(null);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }

            }
        });

    }


    public static class ExceptionTest {
        public void fun3() {

        }

        public static void fun(String s) {
            try {
                fun2(s);
            } catch (Exception e) {
                RuntimeException exception = new RuntimeException("dddddddddddd");
                exception.fillInStackTrace().initCause(e);
                throw exception;
            }

        }

        public static void fun2(String s) {
            s.toString();
        }
    }


}
