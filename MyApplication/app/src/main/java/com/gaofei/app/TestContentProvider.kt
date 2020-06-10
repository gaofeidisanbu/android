package com.gaofei.app

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.gaofei.library.utils.LogUtils

class TestContentProvider: ContentProvider() {
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        LogUtils.d("TestContentProvider query "+ Thread.currentThread().name)
        return null
    }

    override fun onCreate(): Boolean {
        LogUtils.d("TestContentProvider onCreate "+ Thread.currentThread().name)
        return true
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        LogUtils.d("TestContentProvider onCreate")
        return 1
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        LogUtils.d("TestContentProvider onCreate")
        return 0
    }

    override fun getType(uri: Uri): String? {
        return ""
    }
}