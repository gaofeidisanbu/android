package com.example.gaofei.myapplication;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by gaofei on 2017/5/17.
 */

public class BaseAct extends AppCompatActivity{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);}

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("","2");
        Log.d("","3");
        Log.d("","4");
        Log.d("","5");
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("master","1");
        Log.d("master","2");
        Log.d("master","3");
        Log.d("master","4");
        Log.d("master","5");
        Log.d("master","6");
    }
}
