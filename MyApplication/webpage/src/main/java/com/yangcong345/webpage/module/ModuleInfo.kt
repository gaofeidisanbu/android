package com.yangcong345.webpage.module

import java.io.Serializable

/**
 * 该类包含一个module的信息，通过调用[H5ModulePackageManager]注册
 */
class ModuleInfo(val moduleName: String, val clazz: Class<out IH5Module>) : Serializable
