package com.gaofei.app.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.mylibrary.base.BaseAct;
import com.gaofei.app.Instance;
import com.gaofei.app.R;

/**
 * Created by gaofei on 2017/5/17.
 */

public class SkipAct extends BaseAct {
    private static final String TAG = "SkipAct";
    private TextView mTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acty_skip);
        Instance.getInstance(this);
        initView();
        initData();

    }

    private void initView() {
        mTextView = (TextView)findViewById(R.id.text);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blockData();
            }
        });
    }

    private void initData(){

    }

    private void blockData(){
//        for (int i = 0; i < Integer.MAX_VALUE; i++) {
//            for (int j = 0; j < Integer.MAX_VALUE; j++) {
//                Log.d("daddddd", " i = "+i + "j = " + j);
//            }
//        }
        Log.d(TAG,"start");
        try {
            Thread.sleep(8*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"end");
    }
}
