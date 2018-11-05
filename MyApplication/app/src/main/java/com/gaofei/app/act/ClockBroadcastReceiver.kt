package com.gaofei.app.act

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.gaofei.library.utils.LogUtils
import com.gaofei.library.utils.ToastManager

class ClockBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        LogUtils.d("ClockBroadcastReceiver $context")
        ClockStartActivity.intentTo(context)
    }

    companion object {
        const val ACTION = "SIGN_IN_CLOCK"
    }

}