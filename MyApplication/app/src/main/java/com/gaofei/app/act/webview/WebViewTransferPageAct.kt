package com.gaofei.app.act.webview

import android.os.Bundle
import android.os.Handler
import android.webkit.WebView
import com.gaofei.app.R
import com.gaofei.app.databinding.ActWebviewTransferPageBinding
import com.gaofei.library.base.BaseAct
import com.gaofei.library.utils.LogUtils

class WebViewTransferPageAct : BaseAct() {
    private lateinit var binding: ActWebviewTransferPageBinding


    protected var mWebView: WebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActWebviewTransferPageBinding.inflate(layoutInflater)
        supportActionBar?.let {
            it.setTitle("page${pageIndex++}")
        }
        mWebView = findViewById(R.id.webView)
        mWebView?.let {
            preInitWebView(it)
        }
        binding.webView1.setOnClickListener {
            BaseWebViewAct.intentTo(this, "file:///android_asset/html/index.html")
        }
        binding.webView2.setOnClickListener {
            BaseWebViewAct.intentTo(this, "file:///android_asset/html/index.html")
        }
        binding.webView3.setOnClickListener {
            BaseWebViewAct.intentTo(this, "file:///android_asset/html/index.html")
        }
        binding.webView4.setOnClickListener {
            BaseWebViewAct.intentTo(this, "https://www.baidu.com")
        }
        binding.webView5.setOnClickListener {
            BaseWebViewAct.intentTo(this, "https://www.baidu.com")
        }
        binding.webView6.setOnClickListener {
            BaseWebViewAct.intentTo(this, "https://www.baidu.com")
        }
        binding.webView7.setOnClickListener {
            BaseWebViewAct.intentTo(this, "https://www.baidu.com")
        }
        binding.webView8.setOnClickListener {
            BaseWebViewAct.intentTo(this, "https://www.baidu.com")
        }
        binding.webView9.setOnClickListener {
            BaseWebViewAct.intentTo(this, "https://www.baidu.com")
        }
        binding.webView10.setOnClickListener {
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