package com.gaofei.app.widget

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import android.util.AttributeSet
import android.view.MotionEvent

class CustomRecyclerView: androidx.recyclerview.widget.RecyclerView {
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