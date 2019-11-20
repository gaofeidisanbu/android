package com.gaofei.app.act.webview

import android.os.Bundle
import android.os.Handler
import android.webkit.WebView
import com.gaofei.app.R
import com.gaofei.library.base.BaseAct
import com.gaofei.library.utils.LogUtils
import kotlinx.android.synthetic.main.act_webview_transfer_page.*

class WebViewTransferPageAct : BaseAct() {
    protected var mWebView: WebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_webview_transfer_page)
        supportActionBar?.let {
            it.setTitle("page${pageIndex++}")
        }
        mWebView = findViewById(R.id.webView)
        mWebView?.let {
            preInitWebView(it)
        }
        webView1.setOnClickListener {
            BaseWebViewAct.intentTo(this, "file:///android_asset/html/index.html")
        }
        webView2.setOnClickListener {
            BaseWebViewAct.intentTo(this, "file:///android_asset/html/index.html")
        }
        webView3.setOnClickListener {
            BaseWebViewAct.intentTo(this, "file:///android_asset/html/index.html")
        }
        webView4.setOnClickListener {
            BaseWebViewAct.intentTo(this, "https://www.baidu.com")
        }
        webView5.setOnClickListener {
            BaseWebViewAct.intentTo(this, "https://www.baidu.com")
        }
        webView6.setOnClickListener {
            BaseWebViewAct.intentTo(this, "https://www.baidu.com")
        }
        webView7.setOnClickListener {
            BaseWebViewAct.intentTo(this, "https://www.baidu.com")
        }
        webView8.setOnClickListener {
            BaseWebViewAct.intentTo(this, "https://www.baidu.com")
        }
        webView9.setOnClickListener {
            BaseWebViewAct.intentTo(this, "https://www.baidu.com")
        }
        webView10.setOnClickListener {
            BaseWebViewAct.intentTo(this, "https://www.baidu.com")
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mWebView?.let {
            WebViewConfig.webViewDestroy(this, it)
        }
    }

    fun preInitWebView(webView: WebView) {
        WebViewConfig.configWebView(this, webView)
    }

    companion object {
        @JvmStatic
        private var pageIndex = 0
    }

}