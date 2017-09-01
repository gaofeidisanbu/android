package com.example.gaofei.app.act;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.gaofei.app.R;
import com.example.gaofei.app.broadcast.BroadcastReceiverTest;
import com.example.gaofei.app.databinding.ActBroadcastReveiverBinding;
import com.example.mylibrary.base.BaseAct;

/**
 * Created by gaofei on 2017/9/1.
 */

public class BroadcastReceiverAct extends BaseAct {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActBroadcastReveiverBinding binding = DataBindingUtil.setContentView(this,R.layout.act_broadcast_reveiver);
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(BroadcastReceiverTest.ACTION);
                sendBroadcast(intent);
            }
        });

    }
}
