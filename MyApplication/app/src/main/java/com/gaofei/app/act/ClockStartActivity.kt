package com.gaofei.app.act

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import com.gaofei.app.R
import com.gaofei.library.ProjectApplication
import com.gaofei.library.base.BaseAct
import com.gaofei.library.utils.LogUtils
import com.gaofei.library.utils.TextUtils
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_clock_set_up.*
import kotlinx.android.synthetic.main.layout_cock_set_up_week.view.*
import java.text.SimpleDateFormat
import java.util.*


class ClockStartActivity : BaseAct() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clock_start)
    }

    companion object {
        fun intentTo(context: Context) {
            val intent = Intent(context, ClockStartActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }
}

