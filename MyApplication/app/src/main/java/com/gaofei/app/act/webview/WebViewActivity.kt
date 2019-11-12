package com.gaofei.app.act.webview

import android.content.Intent
import android.text.TextUtils
import android.webkit.*
import android.widget.Button
import com.gaofei.app.R

class WebViewActivity : BaseWebViewAct() {

    override fun setWebView() {
        setContentView(R.layout.act_web_view)
        mWebView = findViewById<WebView>(R.id.webView)

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
       val url =  intent?.getStringExtra("url")
        mWebView.loadUrl(url)
    }


    override fun initView() {
        super.initView()
        findViewById<Button>(R.id.button).setOnClickListener {
            val intent = Intent(this, WebViewTransferPageAct::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.button2).setOnClickListener {
           mWebView.loadUrl("https://www.zhihu.com/")
        }
        findViewById<Button>(R.id.button3).setOnClickListener {
           mWebView.loadUrl("https://www.baidu.com/")
        }
    }

    override fun onBackPressed() {
        var canBack = false
        if (mWebView.canGoBack()) {
            val mWebBackForwardList = mWebView.copyBackForwardList()
            if (mWebBackForwardList.size > 0) {
                if (!TextUtils.equals(mWebBackForwardList.getItemAtIndex(0).url, mWebView!!.url)) {
                    canBack = true
                }
            }
        }

        if (canBack) {
            mWebView.goBack()
        } else {
            finish()
        }

    }

}