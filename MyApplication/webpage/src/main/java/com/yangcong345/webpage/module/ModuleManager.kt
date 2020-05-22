package com.yangcong345.webpage.module

/**
 *
 * @Author gaofei
 * @Date 2019/2/22 5:14 PM
 *
 */
class ModuleManager {
    private val mIH5Modules = ArrayList<IH5Module>()

    fun addH5Module(iH5Module: IH5Module) {
        mIH5Modules.add(iH5Module)
    }

    fun getH5Module(moduleName: String): IH5Module? {
        mIH5Modules.forEach {
            if (moduleName == it.getModuleName()) {
                return it
            }
        }
        return null
    }

    fun removeH5Module(iH5Module: IH5Module) {
        mIH5Modules.remove(iH5Module)
    }

    fun removeH5Module(moduleName: String) {
        mIH5Modules.forEach {
            if (moduleName == it.getModuleName()) {
                mIH5Modules.remove(it)
            }
        }
    }
}