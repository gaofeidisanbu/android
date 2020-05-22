package com.yangcong345.webpage.register

import android.app.Activity

import com.yangcong345.webpage.bridge.inter.WebViewService

/**
 * @Author gaofei
 * @Date 2019/1/30 3:21 PM
 */
abstract class IH5RegisterFactory : IActivityResultInterface {
    abstract fun register(activity: Activity, webView: WebViewService)
}
