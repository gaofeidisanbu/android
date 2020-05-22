package com.yangcong345.webpage

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.yangcong345.webpage.module.ModuleInfo
import com.yangcong345.webpage.module.ModuleType

/**
 * @Data 2019-10-29 16:43
 * @author wjt
 * @Description BaseBridgeWebViewV2Activity 导航类
 * @version 1.0
 */
class WebViewV2ActivityNavigator private constructor(val builder: Builder) {

    private val context: Context = builder.context
    private val webPageParam: WebPageParam = builder.webPageParam
    // H5Module 安装类型
    private var moduleType: ModuleType = ModuleType.NONE
    // H5Module 名称，设置名称，认为安装类型为 使用moduleName安装
    private var moduleName: String? = null
    // H5Module 信息，设置moduleInfo，认为安装类型为 使用[ModuleInfo]安装
    private var moduleInfo: ModuleInfo? = null
    // 模块上下文参数
    private var moduleContextData: HashMap<String, Any?>? = null
    // 注入参数
    private var injectData: HashMap<String, Any>? = null
    private var requestCode: Int? = null
    /**
     * 可选Bundle，可以传 null
     * See {@link android.content.Context#startActivity(Intent, Bundle)}
     */
    private var optionsBundle: Bundle? = null

    init {
        moduleName = builder.moduleName
        moduleInfo = builder.moduleInfo
        moduleContextData = builder.moduleContextData
        injectData = builder.injectData
        requestCode = builder.requestCode
        optionsBundle = builder.optionsBundle

        moduleType = if (builder.moduleInfo != null) {
            ModuleType.INFO
        } else if (!builder.moduleName.isNullOrEmpty()) {
            ModuleType.NAME
        } else {
            ModuleType.NONE
        }
    }

    fun navigate() {
        val intent = Intent(context, BaseBridgeWebViewV2Activity::class.java)
        intent.flags = builder.mFlags
        val bundle = Bundle()
        bundle.putSerializable(BaseBridgeWebViewV2Activity.KEY_PARAM, webPageParam)
        bundle.putSerializable(BaseBridgeWebViewV2Activity.KEY_MODULE_TYPE, moduleType)
        bundle.putString(BaseBridgeWebViewV2Activity.KEY_MODULE_NAME, moduleName)
        bundle.putSerializable(BaseBridgeWebViewV2Activity.KEY_MODULE_INFO, moduleInfo)
        bundle.putSerializable(BaseBridgeWebViewV2Activity.KEY_MODULE_CONTEXT_DATA, moduleContextData)
        bundle.putSerializable(BaseBridgeWebViewV2Activity.KEY_MODULE_INJECT_DATA, injectData)

        val isStartActivityForResult = requestCode != null && (context is Activity)
        bundle.putBoolean(BaseBridgeWebViewV2Activity.KEY_IS_START_ACTIVITY_FOR_RESULT, isStartActivityForResult)
        intent.putExtras(bundle)
        if (isStartActivityForResult) {
            (context as Activity).startActivityForResult(intent, requestCode!!, optionsBundle)
        } else {
            context.startActivity(intent, optionsBundle)
        }
    }


    class Builder(val context: Context, val webPageParam: WebPageParam) {

        // H5Module 名称，设置名称，认为安装类型为 使用moduleName安装
        var moduleName: String? = null
        // H5Module 信息，设置moduleInfo，认为安装类型为 使用[ModuleInfo]安装
        var moduleInfo: ModuleInfo? = null
        // 模块上下文参数
        var moduleContextData: HashMap<String, Any?>? = null
        // 注入参数
        var injectData: HashMap<String, Any>? = null
        // requestCode
        var requestCode: Int? = null
        /**
         * Additional options for how the Activity should be started.
         * See {@link android.content.Context#startActivity(Intent, Bundle)}
         */
        var optionsBundle: Bundle? = null

        var mFlags: Int = Intent.FLAG_ACTIVITY_NEW_TASK

        fun build(): WebViewV2ActivityNavigator = WebViewV2ActivityNavigator(this)

    }
}