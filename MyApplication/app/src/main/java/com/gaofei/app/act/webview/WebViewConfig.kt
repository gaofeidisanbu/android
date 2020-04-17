package com.gaofei.app.act.webview

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.*
import com.gaofei.app.BuildConfig
import com.gaofei.library.utils.LogUtils

object WebViewConfig {
    @SuppressLint("SetJavaScriptEnabled")
     fun configWebView(context: Context, webView: WebView) {
        webView.settings.setSupportZoom(true)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.cacheMode = WebSettings.LOAD_DEFAULT
        webView.settings.setSupportZoom(true)
        webView.settings.javaScriptEnabled = true
        webView.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            val uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && BuildConfig.DEBUG) {
            WebView.setWebContentsDebuggingEnabled(true)
        }
        val webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(webView: WebView, s: String?) {
                super.onReceivedTitle(webView, s)
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
            }

            override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                LogUtils.d("WebChromeClient = onJsAlert url = ${url} message = ${message} result = ${result.toString()}")
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
                return true
            }
        }
        webView.webChromeClient = webChromeClient
    }

    fun webViewDestroy(context: Context, webView: WebView) {
        webView.getSettings().setJavaScriptEnabled(false)
        webView.clearFormData()
        webView.clearHistory()
        destroyWebView(webView)
        webView.destroy()
    }

    private fun destroyWebView(webView: WebView) {
        webView.stopLoading()
        webView.removeAllViews()
        if (webView.parent != null) {
            //处理webview无法释放造成的内存泄漏，必须在destroy之前调用
            val parent = webView.parent as ViewGroup
            parent.removeView(webView)
            setConfigCallback(null)
        }
    }

    private fun setConfigCallback(windowManager: WindowManager?) {
        try {
            if (Build.VERSION.SDK_INT > 15) {
                return
            }
            var field = WebView::class.java.getDeclaredField("mWebViewCore")
            field = field.type.getDeclaredField("mBrowserFrame")
            field = field.type.getDeclaredField("sConfigCallback")
            field.isAccessible = true
            val configCallback = field.get(null) ?: return
            field = field.type.getDeclaredField("mWindowManager")
            field.isAccessible = true
            field.set(configCallback, windowManager)
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }
    }

}