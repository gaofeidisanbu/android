package com.example.gaofei.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mylibrary.Test2;

public class MainActivity extends BaseAct {
    private static final String TAG = "MainActivity";
    private ImageView mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
    }

    private void initView2() {
        Log.d("test aaa","rebaseda1122dddddddddddddddddddddddd");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
<<<<<<< HEAD
=======
        Log.d("master 11","1");
        Log.d("master 11","2");
        Log.d("master 11","3");
        Log.d("master 11","4");
        Log.d("master 11","5");
        Log.d("master 11","6");
        Log.d("master 11","7");
        Log.d("master 11","8");
        Log.d("master 11","9");
        Log.d("master 11","11");
        Log.d("master 11","12");
>>>>>>> dev 12
    }
}