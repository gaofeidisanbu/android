package com.yangcong345.webpage.view;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yangcong345.webpage.log.LogUtils;


/**
 * Created by gaofei on 01/03/2018.
 * 该类用于处理，js bridge还没有建立，native已经请求js方法
 */

public class BridgeWebViewClientV2 extends WebViewClient {
    public static final String TAG = "BridgeWebViewClient";
    private boolean isPrepareFinish = false;
    private JsBridgeWebViewV2 mWebView;

    public BridgeWebViewClientV2(JsBridgeWebViewV2 webView) {
        this.mWebView = webView;
    }


    @Override
    public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
        super.onPageStarted(webView, s, bitmap);
        isPrepareFinish = false;
        mWebView.isPrepareFinished(isPrepareFinish);
        LogUtils.d("BridgeWebViewClient = onPageStarted");
    }

    @Override
    public void onPageFinished(WebView webView, String s) {
        super.onPageFinished(webView, s);
        isPrepareFinish = true;
        mWebView.isPrepareFinished(isPrepareFinish);
        LogUtils.d("BridgeWebViewClient = onPageFinished");
    }

    @Override
    public void onReceivedError(WebView webView, int i, String s, String s1) {
        super.onReceivedError(webView, i, s, s1);
        LogUtils.INSTANCE.e(" i = " + i + " s = " + s + " s1 = " + s1);
    }

    @Override
    public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
        super.onReceivedError(webView, webResourceRequest, webResourceError);
        logError(String.format("request = %s error = %s", getWebResourceRequest(webResourceRequest), getWebResourceError(webResourceError)));
    }

    @Override
    public void onReceivedHttpError(WebView webView, WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse) {
        super.onReceivedHttpError(webView, webResourceRequest, webResourceResponse);
        logError(String.format("request = %s error = %s", getWebResourceRequest(webResourceRequest), getWebResourceResponse(webResourceResponse)));
    }

    @Override
    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        super.onReceivedSslError(webView, sslErrorHandler, sslError);
        logError(String.format("request = %s error = %s", getSslErrorHandler(sslErrorHandler), getSslError(sslError)));
    }

    private String getWebResourceRequest(WebResourceRequest webResourceRequest) {
        String format = "%s %s \n";
        StringBuilder sb = new StringBuilder();
        if (webResourceRequest != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                sb.append(String.format(format, "url", webResourceRequest.getUrl()));
                sb.append(String.format(format, "method", webResourceRequest.getMethod()));
                sb.append(String.format(format, "requestHeaders", webResourceRequest.getRequestHeaders()));
                sb.append(String.format(format, "hasGesture", webResourceRequest.hasGesture()));
            }
        }
        return sb.toString();
    }

    private String getWebResourceError(WebResourceError webResourceError) {
        String format = "%s %s \n";
        StringBuilder sb = new StringBuilder();
        if (webResourceError != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                sb.append(String.format(format, "errorCode", webResourceError.getErrorCode()));
                sb.append(String.format(format, "description", webResourceError.getDescription()));
            }
        }
        return sb.toString();
    }

    private String getWebResourceResponse(WebResourceResponse response) {
        String format = "%s %s \n";
        StringBuilder sb = new StringBuilder();
        if (response != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                sb.append(String.format(format, "statusCode", response.getStatusCode()));
                sb.append(String.format(format, "reasonPhrase", response.getReasonPhrase()));
                sb.append(String.format(format, "responseHeaders", response.getResponseHeaders()));
            }
        }
        return sb.toString();
    }

    private String getSslErrorHandler(SslErrorHandler sslErrorHandler) {
        String format = "%s %s \n";
        StringBuilder sb = new StringBuilder();
        if (sslErrorHandler != null) {
        }
        return sb.toString();
    }

    private String getSslError(SslError sslError) {
        String format = "%s %s \n";
        StringBuilder sb = new StringBuilder();
        if (sslError != null) {
            sb.append(String.format(format, "primaryError", sslError.getPrimaryError()));
            sb.append(String.format(format, "method", sslError.getCertificate()));
        }
        return sb.toString();
    }

    private void logError(String error) {
        LogUtils.e(TAG, new Exception(error));
    }

    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        super.onScaleChanged(view, oldScale, newScale);
    }
}
