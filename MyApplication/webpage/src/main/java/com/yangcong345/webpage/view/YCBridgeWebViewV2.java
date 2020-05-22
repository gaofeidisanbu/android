package com.yangcong345.webpage.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.yangcong345.webpage.Module;
import com.yangcong345.webpage.WebPageApi;
import com.yangcong345.webpage.WebPageUtils;
import com.yangcong345.webpage.register.IActivityResultInterface;
import com.yangcong345.webpage.register.IH5RegisterFactory;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Created by gaofei on 2017/7/3.
 * 该类通过RegisterManager的子类，提供一些通用能力
 */

public class YCBridgeWebViewV2 extends JsBridgeWebViewV2 implements IActivityResultInterface {

    public YCBridgeWebViewV2(Context context) {
        super(context);
    }

    public YCBridgeWebViewV2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public YCBridgeWebViewV2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public float getWebViewScale() {
        return mWebViewScale;
    }

    public void setWebViewScale(float mWebViewScale) {
        this.mWebViewScale = mWebViewScale;
    }

    private float mWebViewScale = 1f;
    private List<IH5RegisterFactory> mH5RegisterFactoryList;

    @Override
    protected void init(Context context) {
        super.init(context);
        if (!Module.getContract().isSupportHardwareAcceleratedForAll()) {
            setLayerType(LAYER_TYPE_SOFTWARE, null);
        }
        preRegister(context);
    }


    private void preRegister(Context context) {
        mH5RegisterFactoryList = WebPageApi.INSTANCE.getCommonH5RegisterFactoryList();
        for (IH5RegisterFactory h5RegisterFactory : mH5RegisterFactoryList) {
            h5RegisterFactory.register((Activity) context, this);
        }
    }

    @Override
    protected void configWebView() {
        super.configWebView();
        this.setWebViewClient(new YCWebViewClientV2(this));
        this.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int newProgress) {
                super.onProgressChanged(webView, newProgress);
            }
        });
    }


    @Override
    public void loadUrl(String s) {
        s = WebPageUtils.addUrlPubParam(s);
        super.loadUrl(s);
    }


    @Override
    public void loadData(String s, String s1, String s2) {
        s = WebPageUtils.addUrlPubParam(s);
        super.loadData(s, s1, s2);
    }


    @Override
    public void loadUrl(String s, Map<String, String> map) {
        s = WebPageUtils.addUrlPubParam(s);
        super.loadUrl(s, map);
    }

    @Override
    public void loadDataWithBaseURL(String s, String s1, String s2, String s3, String s4) {
        s = WebPageUtils.addUrlPubParam(s);
        super.loadDataWithBaseURL(s, s1, s2, s3, s4);
    }


    public void setOnWebViewListener(OnWebViewListener mOnWebViewListener) {
        this.mOnWebViewListener = mOnWebViewListener;
    }

    private OnWebViewListener mOnWebViewListener;

    public interface OnWebViewListener {
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnWebViewListener != null) {
            mOnWebViewListener.onScrollChanged(l, t, oldl, oldt);
        }
    }


    @Override
    public void destroy() {
        getSettings().setJavaScriptEnabled(false);
        this.clearFormData();
        this.clearHistory();
        destroyWebView();
        super.destroy();
    }

    private void destroyWebView() {
        this.stopLoading();
        this.removeAllViews();
        if (this.getParent() != null) {
            //处理webview无法释放造成的内存泄漏，必须在destroy之前调用
            ViewGroup parent = (ViewGroup) this.getParent();
            parent.removeView(this);
            setConfigCallback(null);
        }
    }

    public void setConfigCallback(WindowManager windowManager) {
        try {
            if (Build.VERSION.SDK_INT > 15) {
                return;
            }
            Field field = WebView.class.getDeclaredField("mWebViewCore");
            field = field.getType().getDeclaredField("mBrowserFrame");
            field = field.getType().getDeclaredField("sConfigCallback");
            field.setAccessible(true);
            Object configCallback = field.get(null);
            if (null == configCallback) {
                return;
            }
            field = field.getType().getDeclaredField("mWindowManager");
            field.setAccessible(true);
            field.set(configCallback, windowManager);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @NotNull Intent data) {
        for (IH5RegisterFactory h5RegisterFactory : mH5RegisterFactoryList) {
            h5RegisterFactory.onActivityResult(requestCode, resultCode, data);
        }
    }
}