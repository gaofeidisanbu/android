package com.yangcong345.webpage.register

import android.content.Intent

/**
 *
 * @Author gaofei
 * @Date 2019/3/15 1:25 PM
 *
 */
interface IActivityResultInterface {
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {}
}

interface ActivityResultCallBack {
    fun callback(requestCode: Int, resultCode: Int, data: Intent?)
}