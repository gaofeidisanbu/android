package com.gaofei.app2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.gaofei.library.Add;
import com.gaofei.library.base.BaseAct;

public class Test2Activity extends BaseAct {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action_Broadcast_Receiver_Test");
                sendBroadcast(intent);
                Add.fun();
            }
        });
    }


}
