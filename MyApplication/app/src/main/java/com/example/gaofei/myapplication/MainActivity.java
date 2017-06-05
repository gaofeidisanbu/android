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
//        Test test = new Test();
//        test.fun("d");
        Test2 test2 = new Test2();
        test2.aa("");
//        Test test = new Test();
//        test.fun("");
    }


}
