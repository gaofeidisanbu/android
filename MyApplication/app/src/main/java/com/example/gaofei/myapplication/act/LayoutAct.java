package com.example.gaofei.myapplication.act;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.gaofei.myapplication.BaseAct;
import com.example.gaofei.myapplication.R;

/**
 * Created by gaofei on 2017/8/10.
 */

public class LayoutAct extends BaseAct{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_layout);
    }
}
