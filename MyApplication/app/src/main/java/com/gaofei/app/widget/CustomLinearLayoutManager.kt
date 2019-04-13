package com.gaofei.app.widget

import android.content.Context
import android.support.v7.widget.LinearLayoutManager

class CustomLinearLayoutManager(context: Context, val isScrollEnabled: Boolean): LinearLayoutManager(context) {


    override fun canScrollHorizontally(): Boolean {
        return isScrollEnabled && super.canScrollHorizontally()
    }

}