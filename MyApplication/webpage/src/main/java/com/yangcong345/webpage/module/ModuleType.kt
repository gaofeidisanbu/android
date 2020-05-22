package com.yangcong345.webpage.module

enum class ModuleType {
    /**
     *不使用module
     */
    NONE,
    /**
     *  使用moduleName安装
     */
    NAME,
    /**
     * 使用[ModuleInfo]安装
     */
    INFO,

    /**
     * 暂没有使用
     */
    URL_MODULE
}