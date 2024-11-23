package com.gaofei.app2

import android.app.Service
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.IBinder
import android.os.Process
import com.gaofei.library.utils.LogUtils

class EasyService : Service() {

    override fun onCreate() {
        super.onCreate()
        LogUtils.d("$TAG ${" processId = " + Process.myPid() + " threadId = " + Thread.currentThread().id}")
        Thread.sleep(25*1000)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }


    override fun onBind(intent: Intent?): IBinder {
        LogUtils.d("$TAG ${" processId = " + Process.myPid() + " threadId = " + Thread.currentThread().id}")
       return Binder();
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    companion object {
        private val TAG  = "EasyService"
        val ACTION = "com.gaofei.app2.EasyService"
    }

}