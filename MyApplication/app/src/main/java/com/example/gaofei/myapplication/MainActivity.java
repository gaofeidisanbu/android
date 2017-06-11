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
        Log.d("test aaa","rebase");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("master 11","1");
        Log.d("master 11","2");
        Log.d("master 11","3");
    }
}