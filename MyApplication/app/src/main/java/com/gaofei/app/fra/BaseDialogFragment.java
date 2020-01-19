package com.gaofei.app.fra;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.gaofei.app.MainActivity;
import com.gaofei.app.R;
import com.gaofei.library.utils.LogUtils;

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


    public static BaseDialogFragment newInstance(String title, String message, String btnText1, String btnText2, DialogInterface.OnClickListener listener) {
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
        View binding = context.getLayoutInflater().inflate(R.layout.dialog_common_with_2_button, null,false);
        dialog.setContentView(binding);
        TextView titleView= binding.findViewById(R.id.title);
        TextView contentView= binding.findViewById(R.id.content);
        TextView btnText1View= binding.findViewById(R.id.buttonNegative);
        TextView btnText2View= binding.findViewById(R.id.buttonPositive);
        titleView.setText(title);
        contentView.setText(message);
        btnText1View.setText(btnText1);
        btnText2View.setText(btnText2);
        binding.findViewById(R.id.buttonNegative).setOnClickListener(this);
        binding.findViewById(R.id.buttonPositive).setOnClickListener(this);
        dialog.setCanceledOnTouchOutside(true);

        return dialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonNegative:
                if(listener != null){
                    listener.onClick(dialog,DialogInterface.BUTTON_NEGATIVE);
                }
                break;
            case R.id.buttonPositive:
                if(listener != null){
                    listener.onClick(dialog,DialogInterface.BUTTON_POSITIVE);
                }
                Intent intent = new Intent(getContext(),MainActivity.class);
                this.startActivity(intent);
                this.dismiss();

                break;
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        FragmentManager fragmentManager = getFragmentManager();
        LogUtils.d(fragmentManager == null);
    }
}
