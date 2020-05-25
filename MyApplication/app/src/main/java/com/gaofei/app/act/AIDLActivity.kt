package com.gaofei.app.act

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import com.gaofei.app.R
import com.gaofei.app2.IEasyService
import com.gaofei.library.base.BaseAct
import com.gaofei.library.utils.LogUtils
import kotlinx.android.synthetic.main.act_aidl.*

class AIDLActivity : BaseAct() {
    private var easyService: IEasyService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_aidl)
        createService.setOnClickListener {
            val intent = Intent("com.gaofei.app2.EasyService")
            intent.setPackage("com.gaofei.app2")
            bindService(intent, object : ServiceConnection {
                override fun onServiceDisconnected(name: ComponentName?) {
                    LogUtils.d("$TAG onServiceDisconnected")
                }

                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                    LogUtils.d("$TAG onServiceConnected")
                    easyService = IEasyService.Stub.asInterface(service)


                }

            }, Context.BIND_AUTO_CREATE)
        }

        connect.setOnClickListener {
            easyService?.connect((++connectCount).toString())
        }

        disconnect.setOnClickListener {
            easyService?.disConnect((++disConnectCount).toString())
        }
    }

    companion object {
        @JvmStatic
        var  connectCount = 0
        @JvmStatic
        var  disConnectCount = 0
    }
}