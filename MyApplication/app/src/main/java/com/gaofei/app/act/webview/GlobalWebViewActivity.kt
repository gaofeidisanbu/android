package com.gaofei.app.act.webview

import android.content.Intent
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import com.gaofei.app.R
import com.gaofei.app.databinding.ActGlobalWebViewBinding

class GlobalWebViewActivity : BaseWebViewAct() {
    private lateinit var binding: ActGlobalWebViewBinding


    override fun setWebView() {
        binding = ActGlobalWebViewBinding.inflate(layoutInflater)
        val lp = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        val webView = WebViewPool.getWebView()
        binding.webViewContainer.addView(webView)
        this.mWebView = webView
    }

    override fun onPause() {
        super.onPause()
        binding.webViewContainer.removeAllViews()
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
}