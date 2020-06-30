package com.gaofei.app.widget

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class CustomLinearLayoutManager(context: Context, val isScrollEnabled: Boolean): androidx.recyclerview.widget.LinearLayoutManager(context) {


    override fun canScrollHorizontally(): Boolean {
        return isScrollEnabled && super.canScrollHorizontally()
    }

}