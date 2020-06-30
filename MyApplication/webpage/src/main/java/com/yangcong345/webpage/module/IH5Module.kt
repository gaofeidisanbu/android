package com.yangcong345.webpage.module

import android.app.Activity
import androidx.lifecycle.LifecycleObserver
import com.yangcong345.webpage.IWebViewContainer
import com.yangcong345.webpage.bridge.inter.WebViewService
import java.io.Serializable

/**
 * @Author gaofei
 * @Date 2019/1/30 2:59 PM
 * 所有业务module的基类，实现module必须实现该类；具体可参考[BaseModule]
 * 页面业务代码应该写在改类的实现类，需要通过[ModuleInfo]注册
 *
 */
interface IH5Module : Serializable, WebViewService , LifecycleObserver {

    /**
     * module 唯一标示，h5可以通过改name进行已注册的插件
     * 安装
     */
    fun getModuleName(): String?

    /**
     * module url
     */
    fun getModuleUrl(): String

    /**
     * module初始化时，调用该方法
     */
     fun initModule(activity: Activity, moduleName: String, webView: WebViewService, contextMap: MutableMap<String, Any>?,iWebViewContainer: IWebViewContainer?)

    /**
     * 获取该module已注册的所有方法
     */
    fun getModuleHandlerList(): List<String>

    /**
     * 页面销毁
     */
    fun destroy()

}

interface IUrlH5Module: Serializable {
    fun setModuleUrl(url: String)
}