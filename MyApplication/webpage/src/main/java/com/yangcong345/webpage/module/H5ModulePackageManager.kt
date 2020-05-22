package com.yangcong345.webpage.module

import com.yangcong345.webpage.log.LogUtils


/**
 *
 * @Author gaofei
 * @Date 2019/2/13 11:14 AM
 * 注册[ModuleInfo]的管理类
 */
object H5ModulePackageManager {
    const val TAG = "H5ModulePackageManager"
    private val mH5ModuleMap = HashMap<String, ModuleInfo>()

    fun addH5ModulePackage(modulePackage: H5ModulePackage) {
        val list = modulePackage.createH5Modules()
        list.forEach {
            val moduleName = it.moduleName
            val module = mH5ModuleMap[moduleName]
            if (module == null) {
                mH5ModuleMap[moduleName] = it
                LogUtils.d("[$moduleName] has added")
            } else {
                LogUtils.w("[$moduleName] has exists")
            }
        }
    }

    fun getH5Module(moduleName: String): ModuleInfo? {
        return mH5ModuleMap[moduleName]
    }

}

