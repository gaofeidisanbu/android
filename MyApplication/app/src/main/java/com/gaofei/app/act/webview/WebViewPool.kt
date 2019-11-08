package com.gaofei.app.act.webview

import android.webkit.WebView
import com.gaofei.app.AppApplication

object WebViewPool {
    private lateinit var mWebView: WebView

    fun init() {
        mWebView = WebView(AppApplication.getAppContext())
    }
    fun getWebView(): WebView {
        return mWebView
    }

}