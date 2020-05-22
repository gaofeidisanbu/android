package com.yangcong345.webpage

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import java.io.Serializable

interface IActivity : Serializable {

    fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean  = false

    fun onBackPressed(type: ClickBackType): Boolean  = false

    fun browserClose(): Boolean = false

    fun onSaveInstanceState(outState: Bundle) {}

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){}
}