package com.yangcong345.webpage.handler

import com.yangcong345.webpage.YCAction1
import com.yangcong345.webpage.bridge.inter.INavigation
import com.yangcong345.webpage.bridge.inter.WebViewService

class NavigationHandler(val navigation: INavigation) {
    fun register(webViewService: WebViewService) {
        webViewService.registerHandlerByName("browserForward") {
            navigation.browserForward()
        }
        webViewService.registerHandlerByName("browserBack") {
            navigation.browserBack()
        }
        webViewService.registerHandlerByName("browserClose") {
            navigation.browserClose()
        }
        webViewService.registerHandlerByName("browserTitle", YCAction1<Map<String, String>> { map ->
            navigation.browserTitle(map)
        })
        webViewService.registerHandlerByName("browserHideLoading") {
            navigation.browserHideLoading()
        }
    }
}