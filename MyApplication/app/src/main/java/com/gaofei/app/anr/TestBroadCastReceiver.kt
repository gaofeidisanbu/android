package com.gaofei.app.anr

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.gaofei.library.utils.LogUtils

class TestBroadCastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        LogUtils.d("onReceive start")
        val cpu = CPU()
        cpu.test()
        LogUtils.d("onReceive end")
    }


    companion object {
        val ACTION = "TestBroadCastReceiver"
    }

}