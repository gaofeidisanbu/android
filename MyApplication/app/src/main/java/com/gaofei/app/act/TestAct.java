package com.gaofei.app.act;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.gaofei.app.R;
import com.gaofei.app.fra.BaseDialogFragment;
import com.gaofei.library.base.BaseAct;

/**
 * Created by gaofei on 2017/6/29.
 */

public class TestAct extends BaseAct {
    private boolean isToolBarShow = false;

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.act_test);
        TextView text = (TextView) findViewById(R.id.text);
        text.setText(stringFromJNI());
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new BaseDialogFragment().show(getSupportFragmentManager(), "");
                Intent intent = new Intent("android.intent.action_Broadcast_Receiver_Test");
//                intent.setPackage("com.gaofei.app");
                sendBroadcast(intent);
            }
        });
    }


    public native String stringFromJNI();

}
