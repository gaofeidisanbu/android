package com.yangcong345.webpage

import android.view.View
import android.webkit.WebView
import com.yangcong345.webpage.view.YCLoadBridgeWebViewV2

/**
 * @Data 2019-10-30 17:39
 * @author wjt
 * @Description WebView 容器抽象接口。
 * @version 1.0
 */
interface IWebViewContainer {
    /**
     * 获取容器的跟布局View
     * @return View
     */
    fun getRootView(): View?

    /**
     * 获取容器的ToolBar
     * @return View
     */
    fun getToolBar(): View?

    /**
     * 获取 WebView
     * @return WebView
     */
    fun getWebView(): WebView?

    /**
     * 获取YCLoadBridgeWebViewV2
     * @return YCLoadBridgeWebViewV2
     */
    fun getLoadBridgeWebView(): YCLoadBridgeWebViewV2?
}