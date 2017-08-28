package com.example.gaofei.myapplication.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.gaofei.myapplication.BaseAct;
import com.example.gaofei.myapplication.R;
import com.example.gaofei.myapplication.act.view.common.SpecialProgressBar;

/**
 * Created by gaofei on 2017/8/10.
 */

public class LayoutAct extends BaseAct {
    private TextView mTextView;
    String[] strs = {"a", "ab", "cd", "f"};
    int count = 0;
    private SpecialProgressBar mProgress;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_task_sub_common);
//        mTextView = (TextView) findViewById(R.id.text);
//        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mTextView.setText(strs[count]);
//                count++;
//                if (count > 3) {
//                    count = 0;
//                }
//            }
//        });
        mProgress = (SpecialProgressBar) findViewById(R.id.progress);
        mProgress.setProgress(0,50,true);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgress.setProgress(0,50,true);
                throw new IllegalArgumentException();
            }
        });
    }
}
