package com.gaofei.app.act.webview

import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import androidx.annotation.RequiresApi
import android.webkit.SslErrorHandler
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient


import com.gaofei.library.utils.LogUtils

/**
 * Created by gaofei on 01/03/2018.
 * 该类用于处理，js bridge还没有建立，native已经请求js方法
 */

class BridgeWebViewClientV2(private val mWebView: WebView) : WebViewClient() {
    private var isPrepareFinish = false
    private var isFirstEnterPageFinish = true


    override fun onPageStarted(webView: WebView, s: String, bitmap: Bitmap?) {
        super.onPageStarted(webView, s, bitmap)
        isPrepareFinish = false
        isFirstEnterPageFinish = true
        LogUtils.d("BridgeWebViewClient = onPageStarted")
    }

    override fun onPageFinished(webView: WebView, s: String) {
        super.onPageFinished(webView, s)
        isPrepareFinish = true
        if (isPrepareFinish ) {
            mWebViewClientCallback?.pageFinished(isFirstEnterPageFinish)
        }
        isFirstEnterPageFinish = false
        LogUtils.d("BridgeWebViewClient = onPageFinished")
    }

    override fun onReceivedError(webView: WebView, i: Int, s: String, s1: String) {
        super.onReceivedError(webView, i, s, s1)
        LogUtils.e(" i = $i s = $s s1 = $s1")
    }

    override fun onReceivedError(webView: WebView, webResourceRequest: WebResourceRequest, webResourceError: WebResourceError) {
        super.onReceivedError(webView, webResourceRequest, webResourceError)
        logError(String.format("request = %s error = %s", getWebResourceRequest(webResourceRequest), getWebResourceError(webResourceError)))
    }

    override fun onReceivedHttpError(webView: WebView, webResourceRequest: WebResourceRequest, webResourceResponse: WebResourceResponse) {
        super.onReceivedHttpError(webView, webResourceRequest, webResourceResponse)
        logError(String.format("request = %s error = %s", getWebResourceRequest(webResourceRequest), getWebResourceResponse(webResourceResponse)))
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    override fun onReceivedSslError(webView: WebView, sslErrorHandler: SslErrorHandler, sslError: SslError) {
        super.onReceivedSslError(webView, sslErrorHandler, sslError)
        logError(String.format("request = %s error = %s", getSslErrorHandler(sslErrorHandler), getSslError(sslError)))
    }

    private fun getWebResourceRequest(webResourceRequest: WebResourceRequest?): String {
        val format = "%s %s \n"
        val sb = StringBuilder()
        if (webResourceRequest != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                sb.append(String.format(format, "url", webResourceRequest.url))
                sb.append(String.format(format, "method", webResourceRequest.method))
                sb.append(String.format(format, "requestHeaders", webResourceRequest.requestHeaders))
                sb.append(String.format(format, "hasGesture", webResourceRequest.hasGesture()))
            }
        }
        return sb.toString()
    }

    private fun getWebResourceError(webResourceError: WebResourceError?): String {
        val format = "%s %s \n"
        val sb = StringBuilder()
        if (webResourceError != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                sb.append(String.format(format, "errorCode", webResourceError.errorCode))
                sb.append(String.format(format, "description", webResourceError.description))
            }
        }
        return sb.toString()
    }

    private fun getWebResourceResponse(response: WebResourceResponse?): String {
        val format = "%s %s \n"
        val sb = StringBuilder()
        if (response != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                sb.append(String.format(format, "statusCode", response.statusCode))
                sb.append(String.format(format, "reasonPhrase", response.reasonPhrase))
                sb.append(String.format(format, "responseHeaders", response.responseHeaders))
            }
        }
        return sb.toString()
    }

    private fun getSslErrorHandler(sslErrorHandler: SslErrorHandler?): String {
        val format = "%s %s \n"
        val sb = StringBuilder()
        if (sslErrorHandler != null) {
        }
        return sb.toString()
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    private fun getSslError(sslError: SslError?): String {
        val format = "%s %s \n"
        val sb = StringBuilder()
        if (sslError != null) {
            sb.append(String.format(format, "primaryError", sslError.primaryError))
            sb.append(String.format(format, "method", sslError.certificate))
        }
        return sb.toString()
    }

    private fun logError(error: String) {
        LogUtils.e(TAG, Exception(error))
    }

    override fun onScaleChanged(view: WebView, oldScale: Float, newScale: Float) {
        super.onScaleChanged(view, oldScale, newScale)
    }

    private var mWebViewClientCallback: IWebViewClientCallback? = null

    fun setWebViewClientCallback(callback: IWebViewClientCallback) {
        this.mWebViewClientCallback = callback
    }

    interface IWebViewClientCallback {
        fun pageFinished(firstEnterPageFinish: Boolean)
    }

    companion object {
        val TAG = "BridgeWebViewClient"
    }
}
