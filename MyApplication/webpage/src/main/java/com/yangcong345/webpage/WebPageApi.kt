package com.yangcong345.webpage

import com.yangcong345.webpage.module.ModuleH5RegisterFactory
import com.yangcong345.webpage.register.IH5RegisterFactory

/**
 * @Data 2019-12-08 16:11
 * @author wjt
 * @Description
 * @version 1.0
 */
object WebPageApi {

    private val h5RegisterFactoryProviders: ArrayList<() -> IH5RegisterFactory> = arrayListOf({
        ModuleH5RegisterFactory()
    })


    /**
     * 注册H5增强方法注册工厂类
     * Note：该方法建议在app初始化时进行调用，否者不能保证所有WebView拥有所注册的增强能力。
     * @param provider 提供5增强方法注册工厂类的实例
     */
    fun addH5RegisterFactoryProvider(provider: () -> IH5RegisterFactory) {
        h5RegisterFactoryProviders.add(provider)
    }

    fun getCommonH5RegisterFactoryList(): List<IH5RegisterFactory> {
        return h5RegisterFactoryProviders.map {
            it.invoke()
        }
    }
}