package com.gaofei.app.anr

import com.gaofei.library.utils.LogUtils

class CPU {

    fun test() {
        val max: Long = Integer.MAX_VALUE.toLong()
        for (i in 0..max) {
            for (j in 0..max) {
                for (m in 0..max) {
                    for (n in 0..max) {
                        val long: Long = i * j + m * n
//                        LogUtils.d(long)
                    }
                }
            }
        }
    }
}