package com.gaofei.app.act;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaofei.app.R;
import com.gaofei.app.databinding.ActTouchEventBinding;
import com.gaofei.library.base.BaseAct;
import com.gaofei.library.utils.CommonUtils;
import com.gaofei.library.utils.LogUtils;
import com.gaofei.library.utils.ToastManager;

/**
 * Created by gaofei on 07/12/2017.
 */

public class TouchEventAct extends BaseAct {
    private ActTouchEventBinding mBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.act_touch_event);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_swipe_load_more,(ViewGroup) mBinding.getRoot(),false);
        LogUtils.d(view.getClass().getSimpleName());
        mBinding.view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastManager.show("view1");
            }
        });
        mBinding.view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastManager.show("view3"+"bottom = "+mBinding.view4.getBottom()+" top = "+mBinding.view4.getTop());
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            LogUtils.d("bottom = "+mBinding.view4.getBottom()+" top = "+mBinding.view4.getTop());
            mBinding.ll2.scrollTo(20,-100);
        }
    }
}
