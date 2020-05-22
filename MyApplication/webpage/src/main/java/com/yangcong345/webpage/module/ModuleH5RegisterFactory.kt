package com.yangcong345.webpage.module

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import com.yangcong345.webpage.WebPageUtils
import com.yangcong345.webpage.YCHandler
import com.yangcong345.webpage.YCResponseCallback
import com.yangcong345.webpage.bridge.inter.WebViewService
import com.yangcong345.webpage.register.IH5RegisterFactory

/**
 *
 * @Author gaofei
 * @Date 2019/2/12 5:03 PM
 *
 */

class ModuleH5RegisterFactory : IH5RegisterFactory() {

    lateinit var activity: Activity
    lateinit var webView: WebViewService


    override fun register(activity: Activity, webView: WebViewService) {
        this.activity = activity
        this.webView = webView
        registerH5Handler()
    }

    companion object {
        const val TAG = "ModuleH5RegisterFactory"
    }

    private fun registerH5Handler() {
        webView.registerHandlerByName("browserPluginInstall", YCHandler<Map<String, Any>, Map<String, Any>> { requestData, responseCallBack ->
            val moduleName = requestData["pluginName"] as String
            val contextMap = requestData["context"] as Map<String, Any>?
            installModule(moduleName, contextMap, responseCallBack)
        })

        webView.registerHandlerByName("browserPluginUninstall", YCHandler<Map<String, Any>, Map<String, Any>> { requestData, responseCallBack ->
            val moduleName = requestData["pluginName"] as String
            uninstallModule(moduleName, responseCallBack)
        })
    }

    private fun installModule(moduleName: String?, contextMap: Map<String, Any>?, responseCallBack: YCResponseCallback<Map<String, Any>>) {
        val responseMap = HashMap<String, Any>()
        var responseStatus = false
        if (!TextUtils.isEmpty(moduleName)) {
            moduleName?.let { moduleName ->
                // TODO:wjt 需解决H5安装Module  IWebViewContainer为空问题
                val iH5Module = webView.installModule(activity, webView, moduleName, contextMap, null)
                if (iH5Module != null) {
                    responseMap["requestURL"] = WebPageUtils.addUrlPubParam(iH5Module.getModuleUrl())
                    responseStatus = true
                } else {
                    responseStatus = false
                }

            }
        } else {
            responseStatus = false
        }
        responseCallBack.callback(responseStatus, responseMap)
    }

    private fun uninstallModule(moduleName: String?, responseCallBack: YCResponseCallback<Map<String, Any>>) {
        webView.uninstallModule(moduleName)
        responseCallBack.callback(true, null)
    }

}