package com.example.gaofei.myapplication.fra;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.example.gaofei.myapplication.R;
import com.example.gaofei.myapplication.databinding.DialogCommonWith2ButtonBinding;

/**
 * Created by gaofei on 2017/8/28.
 */

public class BaseDialogFragment extends DialogFragment implements View.OnClickListener{
    private Dialog dialog;
    private String title;
    private String message;
    private String btnText1;
    private String btnText2;
    private DialogInterface.OnClickListener listener;


    public static BaseDialogFragment newInstance(String title, String message, String btnText1,String btnText2, DialogInterface.OnClickListener listener) {
        BaseDialogFragment dialog = new BaseDialogFragment();
        dialog.setData(title, message, btnText1,btnText2, listener);
        return dialog;
    }

    private void setData(String title, String message, String btnText1, String btnText2, DialogInterface.OnClickListener listener) {
        this.title = title;
        this.message = message;
        this.btnText1 = btnText1;
        this.btnText2 = btnText2;
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Activity context = getActivity();
        dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DialogCommonWith2ButtonBinding binding = DataBindingUtil.inflate(context.getLayoutInflater(), R.layout.dialog_common_with_2_button, null,false);
        dialog.setContentView(binding.getRoot());
        binding.title.setText(title);
        binding.content.setText(message);
        binding.buttonNegative.setText(btnText1);
        binding.buttonPositive.setText(btnText2);
        binding.buttonNegative.setOnClickListener(this);
        binding.buttonPositive.setOnClickListener(this);
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }

    @Override
    public void onClick(View v) {
        dismissAllowingStateLoss();
        switch (v.getId()){
            case R.id.button_negative:
                if(listener != null){
                    listener.onClick(dialog,DialogInterface.BUTTON_NEGATIVE);
                }
                break;
            case R.id.button_positive:
                if(listener != null){
                    listener.onClick(dialog,DialogInterface.BUTTON_POSITIVE);
                }
                break;
        }
    }
}
