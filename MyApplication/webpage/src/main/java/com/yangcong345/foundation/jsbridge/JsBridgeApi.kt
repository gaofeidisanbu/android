package com.yangcong345.foundation.jsbridge

/**
 * @Data 2019-12-08 18:59
 * @author wjt
 * @Description JsBridgeApi
 * @version 1.0
 */
object JsBridgeApi {
    /**
     * 设置JsBridge内部log是否输出
     * @param logEnable Boolean
     */
    fun setLogEnable(logEnable: Boolean) {
        InternalLogUtil.logEnable = logEnable
    }

}