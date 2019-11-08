package com.gaofei.app.act.webview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.CallSuper
import android.webkit.*
import com.gaofei.library.base.BaseAct

abstract class BaseWebViewAct : BaseAct() {
    private val defaultUrl = "file:///android_asset/html/index.html"
    protected lateinit var mWebView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startTime = System.currentTimeMillis()
        setWebView()
        initView()
        var url = intent.getStringExtra(KEY_URL)
        url?.let {
            url = defaultUrl
        }
        WebViewConfig.configWebView(this, mWebView!!)
        val bridgeWebViewClientV2 = BridgeWebViewClientV2(mWebView!!)
        bridgeWebViewClientV2.setWebViewClientCallback(object : BridgeWebViewClientV2.IWebViewClientCallback {
            override fun pageFinished(firstEnterPageFinish: Boolean) {
                if (firstEnterPageFinish) {
                    val delta = System.currentTimeMillis() - startTime
                    WebViewLogUtil.logTime(delta)
                }
            }

        })
        mWebView!!.webViewClient = bridgeWebViewClientV2
        mWebView!!.loadUrl(url)
    }

    abstract fun setWebView()

    override fun onResume() {
        super.onResume()
        mWebView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mWebView.onPause()
    }

    @CallSuper
    @Override
    open fun initView() {

    }

    override fun onDestroy() {
        super.onDestroy()
       WebViewConfig.webViewDestroy(this,mWebView)
    }



    companion object {
        private const val KEY_URL = "url"
        fun intentTo(context: Context, url: String) {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(KEY_URL, url)
            context.startActivity(intent)
        }
    }
}