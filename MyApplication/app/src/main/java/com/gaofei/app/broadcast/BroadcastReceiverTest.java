package com.gaofei.app.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.gaofei.library.utils.LogUtils;


/**
 * Created by gaofei on 2017/9/1.
 */

public class BroadcastReceiverTest extends BroadcastReceiver {
    public static final String TAG = "GAOFEI:BroadcastReceiverTest";
    public static final String ACTION = "android.intent.action_Broadcast_Receiver_Test";

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtils.d(TAG, " processId = " + android.os.Process.myPid() + " threadId = " + Thread.currentThread().getId());
        try {
            Thread.sleep(1000 * 30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogUtils.d(TAG, " onReceive ");
    }


}
