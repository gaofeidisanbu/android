package com.yangcong345.webpage.page

import android.net.Uri
import android.webkit.ValueCallback

/**
 *
 * @Author gaofei
 * @Date 2019/2/26 3:10 PM
 *
 */
interface OnActivityWebViewInteractionListener {

    fun onReceivedTitle(title: String)

    fun onClosePage()

    fun openImageChooserActivity(valueCallback: ValueCallback<Array<Uri>>?)
}