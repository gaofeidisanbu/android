package com.gaofei.app.act.webview

import android.content.Intent
import android.text.TextUtils
import android.webkit.*
import android.widget.Button
import com.gaofei.app.R
import kotlinx.android.synthetic.main.act_web_view.*

class WebViewActivity : BaseWebViewAct() {

    override fun setWebView() {
        setContentView(R.layout.act_web_view)
        mWebView = findViewById<WebView>(R.id.webView)

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
       val url =  intent?.getStringExtra("url")
        if (url != null) {
            mWebView.loadUrl(url)
        }
    }

    override fun onPause() {
        super.onPause()
//        root.removeView(mWebView)
//        WebViewConfig.webViewDestroy(this, mWebView)
//        finish()
    }


    override fun initView() {
        super.initView()
        findViewById<Button>(R.id.button).setOnClickListener {
            val intent = Intent(this, WebViewTransferPageAct::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.button2).setOnClickListener {
           mWebView.loadUrl("https://www.zhihu.com/")
            throw Exception("")
        }
        findViewById<Button>(R.id.button3).setOnClickListener {
           mWebView.loadUrl("https://www.baidu.com/")
        }
    }



}