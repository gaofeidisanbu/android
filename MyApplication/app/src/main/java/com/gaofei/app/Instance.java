package com.gaofei.app;

import android.content.Context;
import android.util.Log;

import com.gaofei.library.utils.LogUtils;

/**
 * Created by gaofei on 2017/5/17.
 */

public class Instance {
    private static Instance mInstance ;
    private Context mCtx;

    public Instance(Context context) {
        this.mCtx = context;
    }

    public static synchronized Instance getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Instance(context);
        }

        return mInstance;
    }

    public void fun(){
        Log.d("week","1");
        Log.d("week","2");
        Log.d("week","3");
    }

    public void fun2(){
        Log.d("Instance","1");
        Log.d("Instance","2");
        Log.d("Instance","3");
        Log.d("Instance","4");
    }


}