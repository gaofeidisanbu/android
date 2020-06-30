package com.yangcong345.webpage

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.core.content.res.ResourcesCompat.getColor
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.ValueCallback
import android.webkit.WebView
import android.widget.RelativeLayout
import android.widget.Toast
import com.yangcong345.webpage.BaseBridgeWebViewV2Activity.Companion.KEY_IS_START_ACTIVITY_FOR_RESULT
import com.yangcong345.webpage.BaseBridgeWebViewV2Activity.Companion.KEY_MODULE_CONTEXT_DATA
import com.yangcong345.webpage.BaseBridgeWebViewV2Activity.Companion.KEY_MODULE_INFO
import com.yangcong345.webpage.BaseBridgeWebViewV2Activity.Companion.KEY_MODULE_INJECT_DATA
import com.yangcong345.webpage.BaseBridgeWebViewV2Activity.Companion.KEY_MODULE_NAME
import com.yangcong345.webpage.BaseBridgeWebViewV2Activity.Companion.KEY_MODULE_TYPE
import com.yangcong345.webpage.BaseBridgeWebViewV2Activity.Companion.KEY_PARAM
import com.yangcong345.webpage.base.BaseFragment
import com.yangcong345.webpage.bridge.inter.INavigation
import com.yangcong345.webpage.bridge.inter.IWebView
import com.yangcong345.webpage.callback.GoBackListener
import com.yangcong345.webpage.handler.BrowserNavHandler
import com.yangcong345.webpage.handler.NavigationHandler
import com.yangcong345.webpage.log.LogUtils
import com.yangcong345.webpage.module.IH5Module
import com.yangcong345.webpage.module.IUrlH5Module
import com.yangcong345.webpage.module.ModuleInfo
import com.yangcong345.webpage.module.ModuleType
import com.yangcong345.webpage.page.OnActivityWebViewInteractionListener
import com.yangcong345.webpage.toast.OmToastManager
import com.yangcong345.webpage.view.YCBridgeWebViewV2
import com.yangcong345.webpage.view.YCLoadBridgeWebViewV2
import kotlinx.android.synthetic.main.fragment_bridge_webview.*


private const val FILE_CHOOSER_RESULT_CODE = 10001

/**
 * @Data 2019-08-07 17:30
 * @author wjt
 * @Description  参考自BaseBridgeWebViewV2Activity,
 *  TODO:只是完成了BaseBridgeWebViewV2Activity部分功能,全部功能待验证
 *  NOTE: 为保证内部 H5页面的逐层返回,BaseBridgeWebViewFragment 需要监听页面的 onBackPressed()时间,请务必在
 *  对应的Activity onBackPressed() 调用该 Fragment canGoBack() 与 goBack()
 * <p>Example:
 * <pre><code>
 * class MainActivity : AppCompatActivity() {
 *      ....
 *      override fun onBackPressed() {
 *          ....
 *          if (fragment is GoBackListener && fragment.canGoBack()) {
 *              // 表示GoBackListener 消费了Back 事件
 *          } else {
 *              // 表示GoBackListener没有消费 Back 事件，执行默认逻辑
 *              super.onBackPressed()
 *          }
 *       }
 *       ....
 * }
 * </code></pre>
 *
 * @version 1.0
 */
open class BaseBridgeWebViewFragment : BaseFragment(), IToolbar, INavigation, IWebView, IWebViewContainer,
        OnActivityWebViewInteractionListener, GoBackListener {


    lateinit var mRootView: ViewGroup
    protected var mWebView: YCBridgeWebViewV2? = null
    protected var mLoadWebView: YCLoadBridgeWebViewV2? = null

    private var mUrl: String = ""

    protected var mParams: WebPageParam = WebPageParam(url = "")
    private var mToolbarStyle: WebPageToolbarStyle? = null
    private var mModuleType: ModuleType = ModuleType.NONE
    private var mModuleName: String? = null
    private var mModuleInfo: ModuleInfo? = null
    private var mModuleContextData: HashMap<String, Any?>? = null
    private var mInjectData: HashMap<String, Any>? = null
    private var isStartActivityForResult = false


    private var mIActivity: IActivity? = null
    private var mIH5Module: IH5Module? = null

    private var mUploadMessageAboveL: ValueCallback<Array<Uri>>? = null

    /**
     * 用来表示页面是第一次进入还是重新回来
     */
    protected var isFirstEnterPage: Boolean = true


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(R.layout.fragment_bridge_webview, container, false) as ViewGroup
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pageInit()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null && savedInstanceState.getBoolean("entered", false)) {
            OmToastManager.show("系统已关闭该页面", Toast.LENGTH_LONG)
            finish()
            return
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mIActivity?.onSaveInstanceState(outState)
    }

    private fun pageInit() {
        parseParams()
        initView()
        installModule()
        initWebView()
        init()
    }

    /**
     * 解析参数
     */
    private fun parseParams() {
        val bundle = getNotNullArguments()
        mParams = bundle.getSerializable(KEY_PARAM) as? WebPageParam ?: WebPageParam(url = "")
        mToolbarStyle = mParams.webPageToolbarStyle
        mModuleType = bundle.getSerializable(KEY_MODULE_TYPE) as? ModuleType ?: ModuleType.NONE
        mModuleName = bundle.getSerializable(KEY_MODULE_NAME) as String?
        mModuleInfo = bundle.getSerializable(KEY_MODULE_INFO) as ModuleInfo?
        mModuleContextData = bundle.getSerializable(KEY_MODULE_CONTEXT_DATA) as HashMap<String, Any?>?
        mInjectData = bundle.getSerializable(KEY_MODULE_INJECT_DATA) as HashMap<String, Any>?
        isStartActivityForResult = bundle.getBoolean(KEY_IS_START_ACTIVITY_FOR_RESULT, false)
    }

    private fun getNotNullArguments(): Bundle {
        return if (arguments == null) {
            OmToastManager.show("系统已关闭该页面", Toast.LENGTH_LONG)
            finish()
            Bundle()
        } else {
            arguments!!
        }
    }

    private fun initView() {
        mLoadWebView = mRootView.findViewById(R.id.loadWebView)
        initToolbar()
    }

    private fun initToolbar() {
        val isShowToolbar = mParams?.isShowToolbar ?: true
        showToolbar(isShowToolbar)
        mToolbarStyle?.let {
            if (isShowToolbar) {
                rlToolbar.setBackgroundColor(getColor(resources, it.toolbarBackgroundColor, null))
                ivImageLeft.setImageResource(it.leftImageId)
                tvTitle.setTextColor(getColor(resources, it.titleColor, null))
                setCustomTitle(mParams?.title ?: "")
            }
        }
        ivImageLeft.setOnClickListener {
            if (!dispatchBackEvent(ClickBackType.LEFT)) {
                finish()
            }
        }
    }

    private fun setCustomTitle(title: String) {
        tvTitle.text = title
    }

    private fun showToolbar(isShow: Boolean) {
        rlToolbar.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    private fun installModule() {
        when (mModuleType) {
            ModuleType.NAME -> {
                val h5Module = mLoadWebView?.getWebView()?.installModule(activity, mLoadWebView!!.getWebView(), mModuleName, mModuleContextData, this)
                initModule(h5Module)
            }
            ModuleType.INFO -> mModuleInfo?.let {
                val h5Module = mLoadWebView?.getWebView()?.installModule(activity, mLoadWebView!!.getWebView(), it, mModuleContextData, this)
                initModule(h5Module)
            }
            else -> mUrl = mParams?.url ?: ""
        }
    }

    private fun initModule(h5Module: IH5Module?) {
        this.mIH5Module = h5Module
        if (mIH5Module is IActivity) {
            mIActivity = mIH5Module as IActivity
        }
        if (mIH5Module is IUrlH5Module) {
            (mIH5Module as IUrlH5Module).setModuleUrl(mParams?.url ?: "")
        }
        h5Module?.let {
            this.lifecycle.addObserver(h5Module)
        }
        this.mUrl = mIH5Module?.getModuleUrl() ?: ""
    }

    private fun initWebView() {
        mLoadWebView?.initParams(mParams?.isShowProgress ?: true, mParams?.isShowFailPage ?: true)
        mWebView = mLoadWebView?.getWebView()
        mLoadWebView?.mAWWIL = this
        mLoadWebView?.loadPage(mUrl)
        mLoadWebView?.addWebViewClientListener(object : YCLoadBridgeWebViewV2.WebViewClientListener {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                injectData()
            }
        })

        mLoadWebView?.getWebView()?.let {
            NavigationHandler(this).register(it)
            BrowserNavHandler(this).register(it)
        }

    }

    private fun injectData() {
    }

    @CallSuper
    protected open fun init() {

    }


    fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return mIActivity?.onKeyDown(keyCode, event) ?: false
    }


    fun dispatchBackEvent(type: ClickBackType): Boolean {
        return mIActivity?.onBackPressed(type) ?: false || webViewBack()
    }

    override fun goBack(): Boolean {
        return dispatchBackEvent(ClickBackType.PHYSICS)
    }

    private fun webViewBack(): Boolean {
        if (mParams?.canBack == false) {
            return false
        }
        if (mWebView == null) {
            return false
        }
        if (!mWebView!!.canGoBack()) {
            return false
        }
        val copyBackForwardList = mWebView!!.copyBackForwardList()
        val canBack = copyBackForwardList?.size ?: 0 > 0 && copyBackForwardList?.getItemAtIndex(0)?.url !== mWebView!!.url
        if (canBack) {
            mWebView!!.goBack()
            mWebView!!.callHandler("browserGoBackButtonClick")
            return true
        }
        return false
    }


    override fun setToolbarShow(isShow: Boolean) {
        showToolbar(isShow)
    }

    override fun browserForward() {
    }

    override fun browserBack() {
        if (!dispatchBackEvent(ClickBackType.OTHERS)) {
            finish()
        }
    }

    override fun browserClose() {
        if (mIActivity?.browserClose() != true) {
            finish()
        }
    }

    override fun browserTitle(data: MutableMap<String, String>?) {
        data?.get("title")?.let {
            setCustomTitle(it)
        }
    }

    override fun browserHideLoading() {
        mLoadWebView?.hideLoading()
    }

    override fun showShowShareIcon(params: MutableMap<String, String>?, callback: YCResponseCallback<*>?) {
    }

    override fun showCustomerService(params: MutableMap<String, String>?, callback: YCResponseCallback<*>?) {
    }

    override fun callPhone(params: MutableMap<String, String>?, callback: YCResponseCallback<*>?) {
        val phone = params?.get("phone") ?: return LogUtils.e("callPhone phone is null")
        try {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        } catch (e: Exception) {
            OmToastManager.show(String.format("不能直接跳转，号码为：%s，请手动跳转", phone), Toast.LENGTH_LONG)
        }
    }

    override fun onReceivedTitle(receivedTile: String) {
        //页面传入参数标题
        val paramTitle = mParams?.title
        //默认H5页面标题
        val defPageTitle = getString(R.string.srv_web_page_title_def)
        val pageTitle = if (receivedTile.isNotEmpty() && receivedTile != defPageTitle) {
            receivedTile
        } else if (paramTitle?.isNotEmpty() == true) {
            paramTitle
        } else {
            defPageTitle
        }
        setCustomTitle(pageTitle)
    }


    override fun onClosePage() {
        finish()
    }

    private fun finish() {
        activity?.finish()
        mWebView?.callHandler("browserCloseButtonClick")
    }


    private fun webViewDestroy() {
        mWebView?.destroy()
    }


    override fun onPageStart() {
        super.onPageStart()
        mWebView?.onResume()
        if (isFirstEnterPage) {
            //  第一次进入
            isFirstEnterPage = false
            // 跟iOS实现保持一致.
            mWebView?.callHandler("browserPageReResume")
        } else {
            //再次回到页面。
            mWebView?.callHandler("browserPageReResume")
        }

    }

    override fun onPageEnd() {
        super.onPageEnd()
        // 跟iOS实现保持一致.
        mWebView?.callHandler("browserPageWillDisappear")
        mWebView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        webViewDestroy()
    }


    override fun openImageChooserActivity(valueCallback: ValueCallback<Array<Uri>>?) {
        mUploadMessageAboveL = valueCallback
        openImageChooserActivity()
    }

    private fun openImageChooserActivity() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(Intent.createChooser(intent, "Image Chooser"), FILE_CHOOSER_RESULT_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mWebView?.onActivityResult(requestCode, resultCode, data)
        handleFile(requestCode, resultCode, data)
        mIActivity?.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleFile(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode != FILE_CHOOSER_RESULT_CODE || mUploadMessageAboveL == null)
            return
        val results = mutableListOf<Uri>()
        if (resultCode == Activity.RESULT_OK) {
            data?.let {
                val dataString = it.dataString
                val clipData = it.clipData
                if (clipData != null) {
                    for (i in 0 until clipData.itemCount) {
                        val item = clipData.getItemAt(i)
                        results.add(item.uri)
                    }
                }
                if (dataString != null)
                    results.add(Uri.parse(dataString))
            }
        }
        mUploadMessageAboveL?.onReceiveValue(results.toTypedArray())
        mUploadMessageAboveL = null
    }


    override fun getRootView(): View? {
        return mRootView
    }

    override fun getToolBar(): View? {
        return rlToolbar
    }

    override fun getWebView(): WebView? {
        return mLoadWebView?.getWebView()
    }

    override fun getLoadBridgeWebView(): YCLoadBridgeWebViewV2? {
        return mLoadWebView
    }


    companion object {
        fun newInstance(param: WebPageParam, moduleType: ModuleType = ModuleType.NONE,
                        moduleName: String? = null, moduleInfo: ModuleInfo? = null,
                        moduleContextData: HashMap<String, Any?>? = null, injectData: HashMap<String, Any>? = null) =
                BaseBridgeWebViewFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(KEY_PARAM, param)
                        putSerializable(KEY_MODULE_TYPE, moduleType)
                        putString(KEY_MODULE_NAME, moduleName)
                        putSerializable(KEY_MODULE_INFO, moduleInfo)
                        putSerializable(KEY_MODULE_CONTEXT_DATA, moduleContextData)
                        putSerializable(KEY_MODULE_INJECT_DATA, injectData)
                    }
                }
    }

}