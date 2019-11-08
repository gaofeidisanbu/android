package com.gaofei.app.act.webview

import android.content.Intent
import android.webkit.*
import android.widget.Button
import com.gaofei.app.R

class WebViewActivity : BaseWebViewAct() {

    override fun setWebView() {
        setContentView(R.layout.act_web_view)
        mWebView = findViewById<WebView>(R.id.webView)

    }


    override fun initView() {
        super.initView()
        findViewById<Button>(R.id.button).setOnClickListener {
            val intent = Intent(this, WebViewTransferPageAct::class.java)
            startActivity(intent)
        }
    }

}