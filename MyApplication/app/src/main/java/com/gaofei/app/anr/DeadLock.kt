package com.gaofei.app.anr

import com.gaofei.library.utils.LogUtils

class DeadLock: Thread() {
    val leftLocked = Any()
    val rightLocked = Any()

    fun left() {
        synchronized(leftLocked) {
            LogUtils.d(name)
            Thread.sleep(20000)
        }
    }

    fun right() {
        synchronized(leftLocked) {
            LogUtils.d(name)
        }
    }

    override fun run() {
        super.run()
        left()
    }
}
