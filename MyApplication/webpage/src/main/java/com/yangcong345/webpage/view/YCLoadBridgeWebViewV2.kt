package com.yangcong345.webpage.view

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.webkit.*
import android.widget.FrameLayout
import com.yangcong345.webpage.BuildConfig
import com.yangcong345.webpage.R
import com.yangcong345.webpage.log.LogUtils
import com.yangcong345.webpage.page.OnActivityWebViewInteractionListener
import com.yangcong345.webpage.toast.OmToastManager
import kotlinx.android.synthetic.main.yc_bridge_load_v2.view.*

/**
 * Created by gaofei on 2017/7/11.
 */

class YCLoadBridgeWebViewV2 : FrameLayout {
    private var mContext: Context? = null

    private var mUrl: String? = null
    // 用于外部控制是否展示进度条
    var mShowProgress = true
    private var mShowFailPage = true
    private var isFirstPage = true
    private var isLoadFail = false
    private var mCallBack: ILoadWebViewCallBack? = null
    var mAWWIL: OnActivityWebViewInteractionListener? = null

    private var webViewClientListenerList: MutableList<WebViewClientListener> = mutableListOf()

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context)
    }

    private fun init(context: Context) {
        this.mContext = context
        LayoutInflater.from(mContext).inflate(R.layout.yc_bridge_load_v2, this, true)
        failRefresh.setOnClickListener { loadPage(mUrl) }
        failBack.setOnClickListener {
            mAWWIL?.onClosePage()
        }
        configureWebView()
    }


    fun initParams(showProgress: Boolean, showFailPage: Boolean) {
        this.mShowProgress = showProgress
        this.mShowFailPage = showFailPage
        initProgress()
        initFailPage()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }


    private fun initProgress() {
        showProgress(mShowProgress)
    }

    private fun updateProgress(newProgress: Int) {
        LogUtils.d("YCLoadBridgeWebViewV2 $newProgress")
        progress.progress = newProgress
    }

    private fun showProgress(isShow: Boolean) {
        progress.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    private fun initFailPage() {
        showFailPage(false)
    }

    private fun showFailPage(isShow: Boolean) {
        failPage.visibility = if (isShow) View.VISIBLE else View.GONE
    }


    fun setCacheMode(mode: Int) {
        webView1!!.settings.cacheMode = mode
    }


    private fun pageStartLoading() {
        showProgress(mShowProgress)
        updateProgress(0)
    }


    private fun showFailed() {
        isLoadFail = true
        if (mShowFailPage) {
            showFailPage(true)
        }
    }

    /**
     * 该方法用于h5的调用，如果需要控制loading[.setShowLoading]
     */
    fun hideLoading() {
        hide()
    }

    private fun hide() {
        if (!isLoadFail) {
            showProgress(false)
            showFailPage(false)
        }
    }

    fun getWebView(): YCBridgeWebViewV2 {
        return webView1
    }

    protected fun configureWebView() {
        webView1.settings.cacheMode = WebSettings.LOAD_DEFAULT
        webView1.settings.setSupportZoom(true)
        webView1.settings.javaScriptEnabled = true
        webView1.settings.domStorageEnabled = true
        webView1.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            val uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            if (intent.resolveActivity(mContext?.packageManager) != null) {
                mContext!!.startActivity(intent)
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && BuildConfig.DEBUG) {
            WebView.setWebContentsDebuggingEnabled(true)
        }
        val customWebViewClient = CustomWebViewClient(webView1, webViewClientListenerList)
        webView1.webViewClient = customWebViewClient
        val webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(webView: WebView, s: String?) {
                super.onReceivedTitle(webView, s)
                mAWWIL?.onReceivedTitle(s ?: "")
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                updateProgress(newProgress)
            }

            override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                LogUtils.d("WebChromeClient = onJsAlert url = ${url} message = ${message} result = ${result.toString()}")
                if (BuildConfig.DEBUG && message != null) {
                    OmToastManager.show(message)
                }
                result?.cancel()
                return true
            }

            override fun onJsConfirm(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                LogUtils.d("WebChromeClient = onJsConfirm url = ${url} message = ${message} result = ${result.toString()}")
                return super.onJsConfirm(view, url, message, result)
            }

            override fun onJsPrompt(view: WebView?, url: String?, message: String?, defaultValue: String?, result: JsPromptResult?): Boolean {
                LogUtils.d("WebChromeClient = onJsPrompt url = ${url} message = ${message} defaultValue = ${defaultValue} result = ${result.toString()}")
                return super.onJsPrompt(view, url, message, defaultValue, result)
            }

            override fun onJsBeforeUnload(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                LogUtils.d("WebChromeClient = onJsBeforeUnload url = ${url} message = ${message} result = ${result.toString()}")
                return super.onJsBeforeUnload(view, url, message, result)
            }

            override fun onPermissionRequest(request: PermissionRequest?) {
                super.onPermissionRequest(request)
                LogUtils.w("WebChromeClient = onPermissionRequest ${request.toString()}")
            }

            override fun onShowFileChooser(webView: WebView?, filePathCallback: ValueCallback<Array<Uri>>?, fileChooserParams: FileChooserParams?): Boolean {
                mAWWIL?.openImageChooserActivity(filePathCallback)
                return true
            }
        }
        webView1.webChromeClient = webChromeClient
    }

    fun loadPage(url: String?) {
        this.mUrl = url
        webView1.loadUrl(mUrl)
//        webView1.reload()
    }


    private inner class CustomWebViewClient(webView: YCBridgeWebViewV2, val webViewClientListenerList: MutableList<WebViewClientListener>) : YCWebViewClientV2(webView) {

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            if (mCallBack != null) {
                if (mCallBack!!.shouldOverrideUrlLoading(view, url)) {
                    return true
                }
            }
            return super.shouldOverrideUrlLoading(view, url)
        }

        override fun shouldInterceptRequest(webView: WebView, s: String): WebResourceResponse? {
            return super.shouldInterceptRequest(webView, s)
        }

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            LogUtils.d("YCLoadBridgeWebViewV2 onPageStarted")
            isLoadFail = false
//            if (isFirstPage) {
            pageStartLoading()
//            } else {
//                hide()
//            }
            webViewClientListenerList.forEach {
                it.onPageStarted(view, url, favicon)
            }
        }

        override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
            super.onReceivedError(view, request, error)
            //            LogUtils.w(error);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (request.isForMainFrame) {
                    showFailed()
                }
            } else {
                showFailed()
            }
        }

        override fun onReceivedHttpError(webView: WebView, webResourceRequest: WebResourceRequest, webResourceResponse: WebResourceResponse) {
            super.onReceivedHttpError(webView, webResourceRequest, webResourceResponse)
        }

        override fun onReceivedSslError(webView: WebView, sslErrorHandler: SslErrorHandler, sslError: SslError) {
            super.onReceivedSslError(webView, sslErrorHandler, sslError)
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            LogUtils.d("YCLoadBridgeWebViewV2 onPageFinished")
            hide()
            isFirstPage = false

            webViewClientListenerList.forEach {
                it.onPageFinished(view, url)
            }

        }

    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        this.removeView(webView1)
    }

    fun setCallBack(callBack: ILoadWebViewCallBack) {
        this.mCallBack = callBack
    }


    interface ILoadWebViewCallBack {
        fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean
    }

    fun addWebViewClientListener(listener: WebViewClientListener) {
        webViewClientListenerList.add(listener)
    }

    fun removeWebViewClientListener(listener: WebViewClientListener) {
        webViewClientListenerList.remove(listener)
    }


    interface WebViewClientListener {
        fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
        }

        fun onPageFinished(view: WebView, url: String) {
        }
    }


}
