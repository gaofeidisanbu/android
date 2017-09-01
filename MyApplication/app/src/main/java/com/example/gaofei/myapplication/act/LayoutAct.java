package com.example.gaofei.myapplication.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TextView;

import com.example.gaofei.myapplication.R;
import com.example.gaofei.myapplication.act.view.common.SpecialProgressBar;
import com.example.gaofei.myapplication.fra.BaseDialogFragment;
import com.example.mylibrary.base.BaseAct;

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
//                mProgress.setProgress(0,50,true);
//                BaseDialogFragment dialogFragment =  BaseDialogFragment.newInstance("test","cotent","确定","取消",null);
//                dialogFragment.show(getSupportFragmentManager(),"d");
                Intent intent = new Intent(LayoutAct.this,ScreenshotAct.class);
                startActivity(intent);
            }
        });
    }
}
