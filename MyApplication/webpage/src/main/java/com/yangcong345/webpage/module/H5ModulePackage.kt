package com.yangcong345.webpage.module

/**
 *
 * @Author gaofei
 * @Date 2019/2/13 11:29 AM
 *  用于注册[ModuleInfo],可在个模块实现改接口，然后在各模块的Application使用[H5ModulePackageManager.addH5ModulePackage]
 *  注册module信息到jsBridge
 */

interface H5ModulePackage {
    fun createH5Modules(): List<ModuleInfo>

}