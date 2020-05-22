package com.yangcong345.webpage.module

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import com.yangcong345.webpage.*
import com.yangcong345.webpage.bridge.inter.WebViewService

/**
 *
 * @Author gaofei
 * @Date 2019/2/12 3:38 PM
 *  module的抽象实现类
 */

abstract class BaseModule() : IH5Module, IActivity{

    protected var mModuleName: String? = null
    protected var mContext: Context? = null
    private var mWebView: WebViewService? = null
    private val mHandlerList = ArrayList<String>()
    protected var mContextMap: MutableMap<String, Any>? = null


    private val lifecycleObserver: LifecycleObserver = object : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        @CallSuper
        fun onDestroy() {
            destroy()
        }
    }

    @CallSuper
    override fun initModule(activity: Activity, moduleName: String, webView: WebViewService, contextMap: MutableMap<String, Any>?, iWebViewContainer: IWebViewContainer?) {
        this.mContext = activity
        this.mModuleName = moduleName
        this.mWebView = webView
        this.mContextMap = contextMap
        register(activity)
        addLifecycleObserver(activity)
    }

    private fun addLifecycleObserver(activity: Activity) {
        (activity as? AppCompatActivity)?.lifecycle?.addObserver(lifecycleObserver)
    }

    override fun registerHandlerByName(methodName: String, request: YCAction?) {
        mHandlerList.add(methodName)
        mWebView?.registerHandlerByName(methodName, request)
    }

    override fun <R : Any?> registerHandlerByName(methodName: String, request: YCAction2<R>?) {
        mHandlerList.add(methodName)
        mWebView?.registerHandlerByName(methodName, request)
    }

    override fun <T : Any?> registerHandlerByName(methodName: String, request: YCAction1<T>?) {
        mHandlerList.add(methodName)
        mWebView?.registerHandlerByName(methodName, request)
    }

    override fun <T : Any?, R : Any?> registerHandlerByName(methodName: String, request: YCHandler<T, R>?) {
        mHandlerList.add(methodName)
        mWebView?.registerHandlerByName(methodName, request)
    }

    override fun callHandler(methodName: String) {
        mWebView?.callHandler(methodName)
    }

    override fun <T : Any?> callHandler(methodName: String?, t: T) {
        mWebView?.callHandler(methodName, t)
    }

    override fun <T : Any?, R : Any?> callHandler(methodName: String?, t: T, responseCallBack: YCResponseCallback2<R>?) {
        mWebView?.callHandler(methodName, t, responseCallBack)
    }

    override fun <T : Any?, R : Any?> callHandler(methodName: String?, t: T, responseCallBack: YCResponseCallback<R>?) {
        mWebView?.callHandler(methodName, t, responseCallBack)
    }

    override fun removeHandler(methodName: String?) {
        mWebView?.removeHandler(methodName)
    }

    override fun installModule(activity: Activity?, webView: WebViewService?, module: ModuleInfo?, contextMap: MutableMap<String, Any>?, iWebViewContainer: IWebViewContainer?): IH5Module {
        return this
    }

    override fun installModule(activity: Activity?, webView: WebViewService?, module: String?, contextMap: MutableMap<String, Any>?, iWebViewContainer: IWebViewContainer?): IH5Module {
        return this
    }

    override fun getModuleHandlerList(): List<String> {
        return mHandlerList
    }

    override fun uninstallModule(plugin: IH5Module?) {
        mWebView?.uninstallModule(this)
    }

    override fun uninstallModule(moduleName: String?) {
        mWebView?.uninstallModule(moduleName)
    }

    override fun injectScript(script: String?) {
        mWebView?.injectScript(script)
    }

    fun getWebView(): WebViewService? {
        return mWebView
    }

    var h5ControlPhysicsKeyBack = false //h5是否控制物理返回


    /**
     * 用于注册jsBridge方法
     */
    @CallSuper
    open fun register(activity: Activity) {

        registerHandlerByName("isControlPhysicsKeyBack", YCAction1<Map<String, Any>> {
            val control = it["isControl"] as? Boolean
            h5ControlPhysicsKeyBack = (control == true)
        })
    }

    @CallSuper
    override fun onBackPressed(type: ClickBackType): Boolean {
        if (h5ControlPhysicsKeyBack) {
            mWebView?.callHandler("browserPagePhysicsKeyBack", mapOf("type" to type.id))
            return true
        }
        return false
    }


    override fun getModuleName(): String? {
        return mModuleName
    }

    @CallSuper
    override fun destroy() {

    }

}