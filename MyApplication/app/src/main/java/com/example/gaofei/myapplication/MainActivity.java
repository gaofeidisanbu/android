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
        mButton = (ImageView) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SkipAct.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        Log.d("test aaa","dddddd");
        Log.d("test aaa","dddddd");
        Log.d("test aaa","dddddd");
        Log.d("test aaa","dddddd");
        Log.d("test aaa","dddddd");
        Log.d("test aaa","dddddd");
        Log.d("test aaa","dddddd");
        Log.d("test aaa","dddddd");
        Log.d("test aaa","dddddd123");
    }

    private void initView2() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SkipAct.class);
                startActivity(intent);
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SkipAct.class);
                startActivity(intent);
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SkipAct.class);
                startActivity(intent);
            }
        });
        Log.d("test aaa","dddddd");
        Log.d("test aaa","dddddd");
        Log.d("test aaa","dddddd");
        Log.d("test aaa","b1");
        Log.d("test aaa","b2");
        Log.d("test aaa","b3");
        Log.d("test aaa","a1");
        Log.d("test aaa","a2");
        Log.d("test aaa","a3");
        Log.d("test aaa","b4");
        Log.d("test aaa","b5");
        Log.d("test aaa","b6");
        Log.d("test aaa","a4");
        Log.d("test aaa","a5");
        Log.d("test aaa","a6");
        Log.d("test aaa","a7");
        Log.d("test aaa","a8");
    }




}
