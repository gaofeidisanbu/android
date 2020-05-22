package com.yangcong345.webpage.support

private const val ONION_PACKAGE_NAME = "com.yangcong345.android.phone"
private const val ANDROID_ASSET_FILE_FRE_FIX = "file:///android_asset"


/**
 * 是否允许FileUrl 准许访问文件
 * @param url String?
 * @return Boolean
 */
fun isAllowFileAccessFromFileURLs(url: String?): Boolean {
    if (url.isNullOrBlank()) return false
    return url.contains(ONION_PACKAGE_NAME) || url.startsWith(ANDROID_ASSET_FILE_FRE_FIX)
}