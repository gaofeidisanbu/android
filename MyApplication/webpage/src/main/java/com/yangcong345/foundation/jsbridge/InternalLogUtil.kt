package com.yangcong345.foundation.jsbridge

import android.util.Log

/**
 * @author: drawf
 * @date: 2019-08-08
 * @see: <a href=""></a>
 * @description: 模块内部私有的LogUtil
 */
internal object InternalLogUtil {

    var logEnable: Boolean = false

    @JvmStatic
    fun e(tag: String, tr: Throwable) {
        if (logEnable) {
            Log.e(tag, Log.getStackTraceString(tr))
        }
    }

    @JvmStatic
    fun d(tag: String, str: String) {
        if (logEnable) {
            Log.d(tag, str)
        }
    }

}