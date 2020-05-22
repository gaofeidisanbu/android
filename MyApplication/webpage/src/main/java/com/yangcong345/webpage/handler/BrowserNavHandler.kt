package com.yangcong345.webpage.handler

import com.yangcong345.webpage.IToolbar
import com.yangcong345.webpage.YCAction1
import com.yangcong345.webpage.bridge.inter.WebViewService

class BrowserNavHandler(val toolbar: IToolbar) {
    fun register(webViewService: WebViewService) {
        webViewService.registerHandlerByName("setBrowserNavHidden", YCAction1<Map<String, Any>> {
            val isHidden = it["isHidden"] as Boolean
            toolbar.setToolbarShow(!isHidden)
        })
    }
}