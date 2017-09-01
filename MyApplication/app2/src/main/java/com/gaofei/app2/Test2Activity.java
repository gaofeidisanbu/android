package com.gaofei.app2;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gaofei.app2.databinding.ActivityMainBinding;
import com.gaofei.library.Add;
import com.gaofei.library.base.BaseAct;

public class Test2Activity extends BaseAct {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.button.setOnClickListener(new View.OnClickListener() {
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
