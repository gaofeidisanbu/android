package com.example.gaofei.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.gaofei.myapplication.utils.LogUtils;

/**
 * Created by gaofei on 2017/5/17.
 */

public class BaseAct extends AppCompatActivity{
    public static String TAG ;
    private static String COMMON_TAG = "GAOFEI:";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = COMMON_TAG + getClass().getSimpleName();
        Log.d(TAG,"------- onCreate");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG,"------- onRestoreInstanceState");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"------- onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"------- onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"------- onResume");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG,"------- onNewIntent");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"------- onPause");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.d(TAG,"------- onSaveInstanceState");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"------- onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"------- onDestroy");
    }

}
