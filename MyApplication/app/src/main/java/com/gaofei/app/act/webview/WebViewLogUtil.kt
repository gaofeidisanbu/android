package com.gaofei.app.act.webview

import com.gaofei.library.utils.LogUtils


object WebViewLogUtil {
    const val TAG = "WebViewTest"

    @JvmStatic
    fun log(id: String, clazz: String, url: String, method: String) {
        LogUtils.d("$TAG LifeCycle ----> ${id}-${clazz}-${url}-${method}")
    }

    @JvmStatic
    fun logTime(time: Long) {
        LogUtils.d("$TAG Time ----> ${time}")
    }
}