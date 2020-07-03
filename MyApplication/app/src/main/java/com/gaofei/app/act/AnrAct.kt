package com.gaofei.app.act

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import com.gaofei.app.R
import com.gaofei.app.anr.IO
import com.gaofei.app.broadcast.BroadcastReceiverTest
import com.gaofei.app.plugin.AesUtils
import com.gaofei.library.base.BaseAct
import kotlinx.android.synthetic.main.act_anr.*

class AnrAct : BaseAct() {
    private val mHandler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_anr)
        val intentFilter = IntentFilter(BroadcastReceiverTest.ACTION)
        registerReceiver(BroadcastReceiverTest(), intentFilter)
        button.setOnClickListener {
            val intent = Intent()
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)
            intent.action = BroadcastReceiverTest.ACTION
            sendBroadcast(intent)
        }
//        val lock = DeadLock()
//        lock.start()
//        mHandler.postDelayed({
//            lock.right()
//        }, 400)
//        val cpu = CPU()
//        cpu.test()
//        val intent = Intent()
//        intent.action = TestBroadCastReceiver.ACTION
//        this.sendBroadcast(intent)
        IO.aa()
        text.setText(getTex11t())
    }

    private fun getTex11t(): String {
        val data = "20191231-onion"
        val key = "ycmath"
        val tran = "AES"
        val byte = "1111111111111111"
        val aa = AesUtils.encrypt(data)
        return aa.toString()
    }
}