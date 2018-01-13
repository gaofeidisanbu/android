package com.gaofei.app.act;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gaofei.app.R;
import com.gaofei.app.databinding.ActEditTextBinding;
import com.gaofei.library.base.BaseAct;
import com.gaofei.library.utils.LogUtils;
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
            LogUtils.d("editText "
                    +"\n e focus = "+mBinding.edit.isFocusable()
                    +"\n e isFocusableInTouchMode = "+mBinding.edit.isFocusableInTouchMode()
                    +"\n t focus = "+mBinding.text.isFocusable()
                    +"\n t isFocusableInTouchMode = "+mBinding.text.isFocusableInTouchMode()
                    +"\n b1 focus = "+mBinding.button1.isFocusable()
                    +"\n b1 isFocusableInTouchMode = "+mBinding.button1.isFocusableInTouchMode()
                    +"\n b2 focus = "+mBinding.button2.isFocusable()
                    +"\n b2 isFocusableInTouchMode = "+mBinding.button2.isFocusableInTouchMode()
                    +"\n e2 focus = "+mBinding.edit2.isFocusable()
                    +"\n e2 isFocusableInTouchMode = "+mBinding.edit2.isFocusableInTouchMode());
            ToastManager.show("button1");
        });
        mBinding.button2.setOnClickListener( v -> {
            mBinding.button1.setFocusable(false);
            ToastManager.show("button2");
        });
        mBinding.button3.setOnClickListener( v -> {
            mBinding.button1.setFocusableInTouchMode(true);
            mBinding.button2.setFocusableInTouchMode(true);
            ToastManager.show("button3");
        });

        mBinding.button4.setOnClickListener( v -> {
            mBinding.button1.requestFocus();
            LogUtils.d("edit isFocused = "
                    +mBinding.edit.isFocused()
                    +" edit2 isFocused = "
                    +mBinding.edit2.isFocused()
                    +" text isFocused = "
                    +mBinding.text.isFocused()
                    +" button1 isFocused = "
                    +mBinding.button1.isFocused()
                    +" button2 isFocused = "
                    +mBinding.button2.isFocused());
        });

    }
}
