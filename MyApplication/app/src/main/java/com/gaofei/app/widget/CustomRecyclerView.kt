package com.gaofei.app.widget

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.MotionEvent

class CustomRecyclerView: RecyclerView {
    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }


    override fun onTouchEvent(e: MotionEvent?): Boolean {
        return true
    }

}