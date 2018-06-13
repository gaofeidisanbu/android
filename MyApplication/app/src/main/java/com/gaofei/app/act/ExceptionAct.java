package com.gaofei.app.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.gaofei.app.R;
import com.gaofei.library.base.BaseAct;
import com.gaofei.library.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

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
                try {
                    ExceptionTest exception = new ExceptionTest();
                    exception.fun(null);
                } catch (Exception e) {
                    Log.e("ycmaaa", "cdaaaaaaaaa", e);
                }

                try {
                    ExceptionTest exception = new ExceptionTest();
                    exception.fun3(null);
                } catch (Exception e) {
                    Log.e("ycmaaa", "cdaaaaaaaaa", e);
                }

                ExceptionTest exception = new ExceptionTest();
                exception.fun4(null);

            }
        });

    }


    public static class ExceptionTest {


        public void fun(String s) {
            try {
                fun2(s);
            } catch (Exception e) {
                RuntimeException exception = new RuntimeException("dddddddddddd");
                exception.fillInStackTrace().initCause(e);
                throw exception;
            }

        }

        public void fun3(String s) {
            try {
                fun2(s);
            } catch (Exception e) {
                RuntimeException exception = new RuntimeException("dddddddddddd");
                exception.initCause(e);
                throw exception;
            }
        }

        public void fun2(String s) throws JSONException {
            s.toString();
        }

        public void fun4(String s) {
            try {
                fun5(s);
            } catch (JSONException e) {
//                Log.e("ycmaaa", "caaaaaaaaaad", e);
                throw  new IllegalArgumentException("cc", e);
            } catch (Exception e) {
//                Log.e("ycmaaa", "caaaaaaaaaad", e);
                throw new IllegalArgumentException("cc", e);
            }
        }


        public void fun5(String s) throws JSONException {
            new JSONObject(s);
        }
    }


}
