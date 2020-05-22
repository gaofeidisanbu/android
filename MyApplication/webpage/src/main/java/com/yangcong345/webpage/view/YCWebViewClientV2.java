package com.yangcong345.webpage.view;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import com.yangcong345.webpage.toast.OmToastManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaofei on 2018/2/25.
 * 字体和支付相关处理
 * 白名单拦截功能（要求所有的WebViewClient都应该使用该类或该类子类）
 */

public class YCWebViewClientV2 extends BridgeWebViewClientV2 {
    private YCBridgeWebViewV2 mWebView;

    public YCWebViewClientV2(YCBridgeWebViewV2 webView) {
        super(webView);
        this.mWebView = webView;
    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        // ------  对alipays & weixin 目前是支持支付相关的scheme处理（暂时不确定其他情况） -------
        if (url.startsWith("alipays:") || url.startsWith("alipay") || url.startsWith("weixin")) {
            try {
                view.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                return true;
            } catch (Exception e) {
                if (url.startsWith("weixin")) {
                    OmToastManager.show("请先安装微信～");
                }
            }
            return true;
        }


        if (url.startsWith("https://wx.tenpay")) {
            Map<String, String> extraHeaders = new HashMap<>();
            extraHeaders.put("Referer", "https://yangcong345.com");
            view.loadUrl(url, extraHeaders);
            return true;
        }

        // 用于企点客服的跳转协议支持
        if (url.startsWith("mqqwpa:") || url.startsWith("mqqapi:")) {
            try {
                view.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                return true;
            } catch (Exception e) {
                OmToastManager.show("请先安装QQ～");
                return false;
            }
        }
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        return super.shouldInterceptRequest(view, url);
    }

    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        super.onScaleChanged(view, oldScale, newScale);
        this.mWebView.setWebViewScale(newScale);
    }
}
