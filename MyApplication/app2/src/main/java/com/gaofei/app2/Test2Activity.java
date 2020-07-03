package com.gaofei.app2;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.gaofei.library.Add;
import com.gaofei.library.base.BaseAct;

public class Test2Activity extends BaseAct {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setAction("android.intent.action_Broadcast_Receiver_Test");
//                sendBroadcast(intent);
//                Add.fun();
//                Intent intent = new Intent("android.intent.action_Broadcast_Receiver_Test");
//                intent.setPackage("com.gaofei.app");
//                sendBroadcast(intent);
//                ContentResolver cr = getContentResolver();
//                Uri uri = Uri.parse("content://com.test.demo.fileprovider/test");
//                cr.query(uri,null,null,null,null);
                Intent intent = new Intent(Test2Activity.this, EasyService.class);
                startForegroundService(intent);

            }
        });
    }


}
