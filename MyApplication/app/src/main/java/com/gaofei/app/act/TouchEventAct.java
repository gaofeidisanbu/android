package com.gaofei.app.act;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gaofei.app.R;
import com.gaofei.app.databinding.ActTouchEventBinding;
import com.gaofei.library.base.BaseAct;

/**
 * Created by gaofei on 07/12/2017.
 */

public class TouchEventAct extends BaseAct {
    private ActTouchEventBinding mBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.act_touch_event);
    }
}
