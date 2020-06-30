package com.yangcong345.webpage.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.CallSuper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.yangcong345.foundation.jsbridge.JSExecuteCallback;
import com.yangcong345.foundation.jsbridge.JsBridge;
import com.yangcong345.foundation.jsbridge.JsBridgeFactory;
import com.yangcong345.foundation.jsbridge.WebViewProxy;
import com.yangcong345.webpage.IWebViewContainer;
import com.yangcong345.webpage.YCAction;
import com.yangcong345.webpage.YCAction1;
import com.yangcong345.webpage.YCAction2;
import com.yangcong345.webpage.YCHandler;
import com.yangcong345.webpage.YCResponseCallback;
import com.yangcong345.webpage.YCResponseCallback2;
import com.yangcong345.webpage.bridge.OnionJsCallInterceptor;
import com.yangcong345.webpage.bridge.OnionRegisterInterceptor;
import com.yangcong345.webpage.bridge.inter.WebViewService;
import com.yangcong345.webpage.bridge.inter.impl.CommonBridgeProtocol;
import com.yangcong345.webpage.bridge.inter.impl.WebViewServiceImpl;
import com.yangcong345.webpage.module.IH5Module;
import com.yangcong345.webpage.module.ModuleInfo;
import com.yangcong345.webpage.support.FileUrlControlUtilKt;
import com.yangcong345.webpage.support.JavascriptInterfaceCompatible;

import java.util.Map;

/**
 * @Author gaofei
 * @Date 2019/1/29 4:46 PM
 */
public class JsBridgeWebViewV2 extends WebView implements WebViewService {
    private Context mContext;
    private JsBridge mJsBridge;
    private WebViewService mWebService;
    private static long uniqueId = 0;
    private JavascriptInterfaceCompatible javascriptInterfaceCompatible = new JavascriptInterfaceCompatible();
    private Handler mMainThreadHandler = new Handler(Looper.getMainLooper());
    private boolean isPageFinished = false;

    public JsBridgeWebViewV2(Context context) {
        super(context);
        init(context);
    }

    public JsBridgeWebViewV2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public JsBridgeWebViewV2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    @CallSuper
    protected void init(Context context) {
        this.mContext = context;
        configWebView();
        setUpBridge();
        setWebService();
    }

    @SuppressLint("AddJavascriptInterface")
    @CallSuper
    protected void configWebView() {
        WebSettings settings = getSettings();
        settings.setSupportZoom(true);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        this.setWebViewClient(new BridgeWebViewClientV2(this));
        addJavascriptInterface(javascriptInterfaceCompatible, "javascriptInterfaceCompatible");
    }

    @Override
    public void loadUrl(String url, Map<String, String> map) {
        checkAllowFileAccess(url);
        super.loadUrl(url, map);
    }


    @Override
    public void loadUrl(String url) {
        checkAllowFileAccess(url);
        super.loadUrl(url);
    }

    /**
     * 根据url 判断是否可以开启 FileUrl访问文件开关
     * @see: <a href="https://yaq.qq.com/blog/detail/130.html"></a>
     * @param url
     */
    private void checkAllowFileAccess(String url) {
        boolean allowFileAccessFromFileURLs = FileUrlControlUtilKt.isAllowFileAccessFromFileURLs(url);
        if (allowFileAccessFromFileURLs) {
            WebSettings settings = getSettings();
            //允许WebView使用File协议
            settings.setAllowFileAccess(true);
            settings.setAllowUniversalAccessFromFileURLs(true);
            settings.setAllowFileAccessFromFileURLs(true);
        }
    }

    @CallSuper
    protected void setUpBridge() {
        mJsBridge = JsBridgeFactory.createJSBridge(getContext(), new WebViewProxy() {
            @Override
            public void loadUrl(String url) {
                JsBridgeWebViewV2.this.loadUrl(url);
            }

            @SuppressLint("JavascriptInterface")
            @Override
            public void addJavascriptInterface(Object object, String name) {
                JsBridgeWebViewV2.this.addJavascriptInterface(object, name);
            }

            @Override
            public void removeJavascriptInterface(String name) {
                JsBridgeWebViewV2.this.removeJavascriptInterface(name);
            }

            @Override
            public void evaluateJavascript(String script, JSExecuteCallback<String> resultCallback) {
                JsBridgeWebViewV2.this.evaluateJavascript(script, resultCallback);
            }

            @Override
            public boolean isPageFinished() {
                return JsBridgeWebViewV2.this.isPageFinished;
            }

            @Override
            public void injectScript(String script) {
                injectData(script);
            }


        });
        mJsBridge.setJsBridgeRegisterInterceptor(new OnionRegisterInterceptor());
        mJsBridge.setJsBridgeJsCallInterceptor(new OnionJsCallInterceptor());
    }

    @CallSuper
    protected void setWebService() {
        mWebService = new WebViewServiceImpl(mJsBridge, new CommonBridgeProtocol());
    }

    private JsBridge getJSBridge() {
        return mJsBridge;
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    /**
     * 执行js方法
     *
     * @param script
     * @param callback
     */
    public void evaluateJavascript(String script, JSExecuteCallback<String> callback) {
        evaluateJavascriptInUIThread(script, callback);
    }

    public void injectData(String script) {
        if (!TextUtils.isEmpty(script)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                super.evaluateJavascript(script, null);
            } else {
                this.loadUrl("javascript:" + script);
            }
        }
    }

    /**
     * webView 方法调用在统一线程
     *
     * @param script
     * @param callback
     */
    private void evaluateJavascriptInUIThread(String script, JSExecuteCallback<String> callback) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            mMainThreadHandler.post(() -> evaluateJavascriptWithCompatibility(script, callback));
        } else {
            evaluateJavascriptWithCompatibility(script, callback);
        }
    }

    /**
     * 兼容KITKAT以下版本
     *
     * @param script
     * @param callback
     */
    private void evaluateJavascriptWithCompatibility(String script, JSExecuteCallback<String> callback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                super.evaluateJavascript(script, s -> {
                    if (callback != null) {
                        callback.onReceiveValue(s);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                evaluateJavascriptInLowVersion(script, callback);
            }
        } else {
            evaluateJavascriptInLowVersion(script, callback);
        }
    }

    private void evaluateJavascriptInLowVersion(String script, JSExecuteCallback<String> callback) {
        if (callback != null) {
            javascriptInterfaceCompatible.addCallback(++uniqueId + "", callback);
            this.loadUrl("javascript:window." + javascriptInterfaceCompatible
                    + ".onResultForScript(" + uniqueId + "," + script + ")");
        } else {
            this.loadUrl("javascript:" + script);
        }
    }


    @Override
    public void registerHandlerByName(String method, YCAction request) {
        mWebService.registerHandlerByName(method, request);
    }

    @Override
    public <T> void registerHandlerByName(String method, YCAction1<T> request) {
        mWebService.registerHandlerByName(method, request);
    }

    @Override
    public <R> void registerHandlerByName(String method, YCAction2<R> request) {
        mWebService.registerHandlerByName(method, request);
    }

    @Override
    public <T, R> void registerHandlerByName(String method, YCHandler<T, R> request) {
        mWebService.registerHandlerByName(method, request);
    }

    @Override
    public void callHandler(String method) {
        mWebService.callHandler(method);
    }

    @Override
    public <T> void callHandler(String method, T t) {
        mWebService.callHandler(method, t);
    }

    @Override
    public <T, R> void callHandler(String method, T t, YCResponseCallback<R> responseCallBack) {
        mWebService.callHandler(method, t, responseCallBack);
    }

    @Override
    public <T, R> void callHandler(String method, T t, YCResponseCallback2<R> responseCallBack) {
        mWebService.callHandler(method, t, responseCallBack);
    }

    @Override
    public void injectScript(String script) {
        mWebService.injectScript(script);
    }

    @Override
    public IH5Module installModule(Activity activity, WebViewService webView, ModuleInfo module, Map<String, Object> contextMap, IWebViewContainer iWebViewContainer) {
        return mWebService.installModule(activity, webView, module, contextMap, iWebViewContainer);
    }

    @Override
    public IH5Module installModule(Activity activity, WebViewService webView, String moduleName, Map<String, Object> contextMap, IWebViewContainer iWebViewContainer) {
        return mWebService.installModule(activity, webView, moduleName, contextMap, iWebViewContainer);
    }

    @Override
    public void uninstallModule(IH5Module module) {
        mWebService.uninstallModule(module);
    }

    @Override
    public void uninstallModule(String moduleName) {
        mWebService.uninstallModule(moduleName);
    }

    @Override
    public void removeHandler(String methodName) {
        mWebService.removeHandler(methodName);
    }

    public void isPrepareFinished(boolean isPrepare) {
        if (isPageFinished != isPrepare) {
            this.isPageFinished = isPrepare;
            if (isPrepare) {
                mJsBridge.pageFinished();
            }
        }
    }


}
