package com.gaofei.app.act;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.gaofei.app.MainActivity;
import com.gaofei.app.R;
import com.gaofei.app.act.view.common.SpecialProgressBar;
import com.gaofei.app.databinding.LayoutTaskSubCommonBinding;
import com.gaofei.app.fra.BaseDialogFragment;
import com.gaofei.library.base.BaseAct;
import com.gaofei.library.utils.LogUtils;

/**
 * Created by gaofei on 2017/8/10.
 */

public class LayoutAct extends BaseAct {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutTaskSubCommonBinding mBinding = DataBindingUtil.setContentView(this, R.layout.layout_task_sub_common);
        BaseDialogFragment dialogFragment = BaseDialogFragment.newInstance("test", "cotent", "确定", "取消", null);
        mBinding.button1.setOnClickListener(v -> dialogFragment.show(getSupportFragmentManager(), "d"));
        mBinding.button2.setOnClickListener(v -> {

        });
    }
}
