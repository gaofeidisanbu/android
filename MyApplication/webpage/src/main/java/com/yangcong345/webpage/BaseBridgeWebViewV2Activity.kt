package com.yangcong345.webpage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import com.yangcong345.webpage.bridge.inter.INavigation
import com.yangcong345.webpage.bridge.inter.IWebView
import com.yangcong345.webpage.module.ModuleInfo
import com.yangcong345.webpage.module.ModuleType
import java.io.Serializable

/**
 * Created by gaofei on 2017/7/4.
 * 主要逻辑已经迁移到 BaseBridgeWebViewFragment
 */

class BaseBridgeWebViewV2Activity : AppCompatActivity(), IWebView {


    protected lateinit var mWebViewFragment: BaseBridgeWebViewFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bridge_webview_v2)
        initFragment()
    }

    private fun initFragment() {
        mWebViewFragment = BaseBridgeWebViewFragment()
        mWebViewFragment.arguments = intent.extras
        supportFragmentManager.beginTransaction().replace(R.id.flFragmentContainer, mWebViewFragment).commitAllowingStateLoss()
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (mWebViewFragment.onKeyDown(keyCode, event)) return true
        return super.onKeyDown(keyCode, event)
    }


    override fun showShowShareIcon(params: MutableMap<String, String>?, callback: YCResponseCallback<*>?) {
        // TODO:WJT需要迁移
        mWebViewFragment.showShowShareIcon(params, callback)
    }

    override fun showCustomerService(params: MutableMap<String, String>?, callback: YCResponseCallback<*>?) {
        // TODO:WJT需要迁移
        mWebViewFragment.showCustomerService(params, callback)
    }

    override fun callPhone(params: MutableMap<String, String>?, callback: YCResponseCallback<*>?) {
        // TODO:WJT需要迁移
        mWebViewFragment.callPhone(params, callback)
    }

    override fun onBackPressed() {
        if (!mWebViewFragment.dispatchBackEvent(ClickBackType.PHYSICS)) {
            super.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Module.getContract().onBaseBridgeWebViewActivityResult(this, requestCode, resultCode, data)
        mWebViewFragment.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        const val KEY_PARAM = "extra.param"
        const val KEY_MODULE_TYPE = "extra.module.type"
        const val KEY_MODULE_NAME = "extra.module.name"
        const val KEY_MODULE_INFO = "extra.module.info"
        const val KEY_MODULE_CONTEXT_DATA = "extra.module.context.data"
        const val KEY_MODULE_INJECT_DATA = "extra.module.injectdata"
        const val KEY_IS_START_ACTIVITY_FOR_RESULT = "extra.is_start.activity.for_result"

        fun navigateTo(context: Context, param: WebPageParam) {
            navigateTo(context, param, ModuleType.NONE, null, null, null, null)
        }

        fun navigateTo(context: Context, param: WebPageParam, moduleName: String, moduleContextData: HashMap<String, Any?>?, injectData: HashMap<String, Any>? = null) {
            navigateTo(context, param, ModuleType.NAME, moduleName, null, moduleContextData, injectData)
        }

        fun navigateTo(context: Context, param: WebPageParam, moduleInfo: ModuleInfo, moduleContextData: HashMap<String, Any?>?) {
            navigateTo(context, param, ModuleType.INFO, null, moduleInfo, moduleContextData, null)
        }

        fun navigateTo(context: Context, param: WebPageParam, moduleInfo: ModuleInfo, moduleContextData: HashMap<String, Any?>?, injectData: HashMap<String, Any>?) {
            navigateTo(context, param, ModuleType.INFO, null, moduleInfo, moduleContextData, injectData)
        }

        private fun navigateTo(context: Context, param: WebPageParam, moduleType: ModuleType, moduleName: String?, moduleInfo: ModuleInfo?, moduleContextData: HashMap<String, Any?>?, injectData: HashMap<String, Any>?) {
            val intent = Intent(context, BaseBridgeWebViewV2Activity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            val bundle = Bundle()
            bundle.putSerializable(KEY_PARAM, param)
            bundle.putSerializable(KEY_MODULE_TYPE, moduleType)
            bundle.putString(KEY_MODULE_NAME, moduleName)
            bundle.putSerializable(KEY_MODULE_INFO, moduleInfo)
            bundle.putSerializable(KEY_MODULE_CONTEXT_DATA, moduleContextData)
            bundle.putSerializable(KEY_MODULE_INJECT_DATA, injectData)
            bundle.putBoolean(KEY_IS_START_ACTIVITY_FOR_RESULT, false)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }

    }


}

enum class ClickBackType(val id: String) {
    /**
     * 物理返回键
     */
    PHYSICS("physics"),

    /**
     * android 自定义Toolbar左上角 返回键
     */
    LEFT("left"),

    /**
     *  其它，可能是h5调用
     */
    OTHERS("others")
}

data class WebPageParam @JvmOverloads constructor(var url: String, val title: String = "",
                                                  val isShowToolbar: Boolean = true,
                                                  val isShowProgress: Boolean = true,
                                                  val isShowFailPage: Boolean = true,
                                                  val canBack: Boolean = true,
                                                  val from: Int = 0,
                                                  val webPageToolbarStyle: WebPageToolbarStyle? = null,
        // 强制使用传入的Title
                                                  val forceSpecifyTitle: Boolean = false,
        //是否执行WebView单实例优化
                                                  val isOptimized: Boolean = true) : Serializable

data class WebPageToolbarStyle(@DrawableRes val leftImageId: Int = R.drawable.icon_on_dark, @ColorRes val titleColor: Int = R.color.webpage_toolbar_title_color_default, @ColorRes val toolbarBackgroundColor: Int = R.color.webpage_toolbar_background_color_default) : Serializable

