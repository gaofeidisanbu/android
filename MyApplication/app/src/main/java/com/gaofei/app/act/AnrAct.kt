package com.gaofei.app.act

import android.os.Bundle
import android.os.Handler
import com.gaofei.app.anr.DeadLock
import com.gaofei.library.base.BaseAct

class AnrAct : BaseAct() {
    private val mHandler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val lock = DeadLock()
        lock.start()
        mHandler.postDelayed({
            lock.right()
        }, 400)
    }
}