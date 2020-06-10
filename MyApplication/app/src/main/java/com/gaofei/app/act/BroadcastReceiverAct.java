package com.gaofei.app.act;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.gaofei.app.R;
import com.gaofei.app.broadcast.BroadcastReceiverTest;
import com.gaofei.library.base.BaseAct;

/**
 * Created by gaofei on 2017/9/1.
 */

public class BroadcastReceiverAct extends BaseAct {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter intentFilter = new IntentFilter(BroadcastReceiverTest.ACTION);
        registerReceiver(new BroadcastReceiverTest(), intentFilter);
        setContentView( R.layout.act_broadcast_reveiver);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(BroadcastReceiverTest.ACTION);
                sendBroadcast(intent);
            }
        });

    }
}
