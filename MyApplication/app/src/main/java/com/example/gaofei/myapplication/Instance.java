package com.example.gaofei.myapplication;

import android.content.Context;

/**
 * Created by gaofei on 2017/5/17.
 */

public class Instance {
    private static Instance mInstance = null;
    private Context mCtx;

    private Instance(Context context) {
        this.mCtx = context;
    }

    public static synchronized Instance getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Instance(context);
        }

        return mInstance;
    }

public void fun1(){}
public void fun2(){}
public void fun3(){}
public void fun4(){}
public void fun5(){}
}
