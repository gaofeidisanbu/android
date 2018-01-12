package com.gaofei.app.act;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gaofei.app.R;
import com.gaofei.app.databinding.ActEditTextBinding;
import com.gaofei.library.base.BaseAct;
import com.gaofei.library.utils.ToastManager;

/**
 * Created by gaofei on 12/01/2018.
 */

public class EditTextAct extends BaseAct {
    private ActEditTextBinding mBinding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.act_edit_text);
        mBinding.button1.setOnClickListener( v -> {
            mBinding.edit.setOnClickListener( e -> {
                ToastManager.show("button1 onclick");
            });
            mBinding.edit.setFocusable(false);
        });
        mBinding.button2.setOnClickListener( v -> {
            mBinding.edit.setFocusable(true);
//            mBinding.edit.setFocusableInTouchMode(true);
            mBinding.edit.setOnClickListener(null);
            ToastManager.show(mBinding.edit.isFocused()+"requestFocus =  "+mBinding.edit.requestFocus());
        });

    }
}
