package com.gaofei.app2

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.gaofei.library.utils.LogUtils

class EasyService : Service() {

    override fun onCreate() {
        super.onCreate()
    }


    override fun onBind(intent: Intent?): IBinder? {
        val service = object : IEasyService.Stub(){
            override fun connect(mes: String?) {
                LogUtils.d("$TAG connect $mes")
            }

            override fun disConnect(mes: String?) {
                LogUtils.d("$TAG disConnect $mes")
            }

        }
        return service
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    companion object {
        private val TAG  = "EasyService"
        val ACTION = "com.gaofei.app2.EasyService"
    }

}